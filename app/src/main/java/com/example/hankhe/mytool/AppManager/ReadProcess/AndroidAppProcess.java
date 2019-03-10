

package com.example.hankhe.mytool.AppManager.ReadProcess;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;

import java.io.IOException;

public class AndroidAppProcess extends AndroidProcess {

  /** {@code true} if the process is in the foreground */
  public boolean foreground;

  /** The user id of this process. */
  public int uid;

  private final Cgroup cgroup;

  public AndroidAppProcess(int pid) throws IOException, NotAndroidAppProcessException {
    super(pid);
    cgroup = super.cgroup();
    ControlGroup cpuacct = cgroup.getGroup("cpuacct");
    ControlGroup cpu = cgroup.getGroup("cpu");
    if (cpu == null || cpuacct == null || !cpuacct.group.contains("pid_")) {
      throw new NotAndroidAppProcessException(pid);
    }
    foreground = !cpu.group.contains("bg_non_interactive");
    try {
      uid = Integer.parseInt(cpuacct.group.split("/")[1].replace("uid_", ""));
    } catch (Exception e) {
      uid = status().getUid();
    }
  }

  /**
   * @return the app's package name
   * @see #name
   */
  public String getPackageName() {
    return name.split(":")[0];
  }


  public PackageInfo getPackageInfo(Context context, int flags)
      throws PackageManager.NameNotFoundException {
    return context.getPackageManager().getPackageInfo(getPackageName(), flags);
  }

  @Override public Cgroup cgroup() {
    return cgroup;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeParcelable(cgroup, flags);
    dest.writeByte((byte) (foreground ? 0x01 : 0x00));
  }

  protected AndroidAppProcess(Parcel in) {
    super(in);
    cgroup = in.readParcelable(Cgroup.class.getClassLoader());
    foreground = in.readByte() != 0x00;
  }

  public static final Creator<AndroidAppProcess> CREATOR = new Creator<AndroidAppProcess>() {

    @Override public AndroidAppProcess createFromParcel(Parcel source) {
      return new AndroidAppProcess(source);
    }

    @Override public AndroidAppProcess[] newArray(int size) {
      return new AndroidAppProcess[size];
    }
  };

  public static final class NotAndroidAppProcessException extends Exception {

    public NotAndroidAppProcessException(int pid) {
      super(String.format("The process %d does not belong to any application", pid));
    }
  }

}
