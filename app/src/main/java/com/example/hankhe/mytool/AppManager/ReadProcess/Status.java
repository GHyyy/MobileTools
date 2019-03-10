/*
 * Copyright (C) 2015. Jared Rummler <jared.rummler@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.hankhe.mytool.AppManager.ReadProcess;

import java.io.IOException;


public final class Status extends ProcFile {


  public static Status get(int pid) throws IOException {
    return new Status(String.format("/proc/%d/status", pid));
  }

  private Status(String path) throws IOException {
    super(path);
  }

  public String getValue(String fieldName) {
    String[] lines = content.split("\n");
    for (String line : lines) {
      if (line.startsWith(fieldName + ":")) {
        return line.split(fieldName + ":")[1].trim();
      }
    }
    return null;
  }

  /**
   * @return The process' UID or -1 if parsing the UID failed.
   */
  public int getUid() {
    try {
      return Integer.parseInt(getValue("Uid").split("\\s+")[0]);
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * @return The process' GID or -1 if parsing the GID failed.
   */
  public int getGid() {
    try {
      return Integer.parseInt(getValue("Gid").split("\\s+")[0]);
    } catch (Exception e) {
      return -1;
    }
  }

}
