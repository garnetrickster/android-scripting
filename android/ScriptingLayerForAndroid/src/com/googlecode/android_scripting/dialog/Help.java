/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.googlecode.android_scripting.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;

import com.googlecode.android_scripting.R;
import com.googlecode.android_scripting.interpreter.InterpreterConstants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.connectbot.HelpActivity;

public class Help {
  private Help() {
    // Utility class.
  }

  private static int helpChecked = 0;

  public static boolean checkApiHelp(Context context) {
    byte[] buf = new byte[1024];
    if (helpChecked == 0) {
      try {
        File dest = new File(InterpreterConstants.SDCARD_SL4A_DOC);
        if (!dest.exists()) {
          dest.mkdirs();
        }
        new File(InterpreterConstants.SDCARD_SL4A_DOC, "index.html");
        AssetManager am = context.getAssets();
        ZipInputStream z = new ZipInputStream(am.open("sl4adoc.zip"));
        ZipEntry e;
        while ((e = z.getNextEntry()) != null) {
          File f = new File(InterpreterConstants.SDCARD_SL4A_DOC, e.getName());
          if (!f.exists() || f.lastModified() < e.getTime()) {
            if (!f.exists() && !f.getParentFile().exists()) {
              f.getParentFile().mkdirs();
            }
            OutputStream o = new BufferedOutputStream(new FileOutputStream(f));
            int len;
            while ((len = z.read(buf)) > 0) {
              o.write(buf, 0, len);
            }
            o.flush();
            o.close();
            f.setLastModified(e.getTime());
          }
        }
        helpChecked = 1;
      } catch (IOException e) {
        helpChecked = -1;
        return false;
      }
    }
    return helpChecked > 0;
  }

  public static void show(final Activity activity) {
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    List<CharSequence> list = new Vector<CharSequence>();
    list.add("Wiki Documentation");
    list.add("YouTube Screencasts");
    list.add("Terminal Help");
    if (checkApiHelp(activity)) {
      list.add("API Help");
    }
    CharSequence[] mylist = list.toArray(new CharSequence[list.size()]);
    builder.setItems(mylist, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which) {
        case 0: {
          Intent intent = new Intent();
          intent.setAction(Intent.ACTION_VIEW);
          intent.setData(Uri.parse(activity.getString(R.string.wiki_url)));
          activity.startActivity(intent);
          break;
        }
        case 1: {
          Intent intent = new Intent();
          intent.setAction(Intent.ACTION_VIEW);
          intent.setData(Uri.parse(activity.getString(R.string.youtube_url)));
          activity.startActivity(intent);
          break;
        }
        case 2: {
          Intent intent = new Intent(activity, HelpActivity.class);
          activity.startActivity(intent);
          break;
        }
        case 3: {
          Intent intent = new Intent();
          intent.setAction(Intent.ACTION_VIEW);
          Uri uri = Uri.fromFile(new File(InterpreterConstants.SDCARD_SL4A_DOC, "index.html"));
          intent.setDataAndType(uri, "text/html");
          activity.startActivity(intent);

        }
        }
      }
    });
    builder.show();
  }
}
