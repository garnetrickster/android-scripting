/*
 * Copyright (C) 2009 Google Inc.
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

package com.googlecode.perlforandroid;

import com.google.ase.interpreter.AseHostedInterpreter;

public class PerlDescriptor extends AseHostedInterpreter {

  private final static String PERL = "perl";

  public String getExtension() {
    return ".pl";
  }

  public String getName() {
    return PERL;
  }

  public String getNiceName() {
    return "Perl 5.10.1";
  }

  public boolean hasInterpreterArchive() {
    return true;
  }

  public boolean hasExtrasArchive() {
    return true;
  }

  public boolean hasScriptsArchive() {
    return true;
  }

  public String getBinary() {
    return PERL;
  }

  public int getVersion() {
    return 7;
  }

  @Override
  public int getScriptsVersion() {
    return 6;
  }

  public String getEmptyParams() {
    return " -de 1";
  }

  public String getExecuteParams() {
    return " %s";
  }
}
