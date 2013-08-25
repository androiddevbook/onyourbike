package com.androiddevbook.onyourbike.chapter4;

import android.app.Application;

/**
 * OnYourBike.java
 * 
 * "On Your Bike" application from "Learning Android Development" published by
 * Addison-Wesley which is an imprint of Pearson.
 * 
 * For more information on this application and the book please visit the
 * Learning Android Development web site:
 * http://www.androiddevbook.com
 * 
 * Or email us:
 * questions@androiddevbook.com
 * 
 * Or contact the authors at:
 * justin@androiddevbook.com or james@androiddevbook.com
 * 
 * Or follow us on twitter:
 * 
 * @androiddevbook @justinmclean @jamesjtalbot
 * 
 * Or find us on Google+:
 * https://plus.google.com/101355380104954686723
 * 
 * The latest version of this code can be found on GitHub
 * https://github.com/androidDevBook/OnYourBike
 * 
 * Copyright [2013] Pearson Education, Inc
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
 * 
 * @author androiddevbook.com
 * @version 1.0
 */
public class OnYourBike extends Application {
    protected Settings settings;

    /**
     * Returns the application settings, creates the settings if they don't
     * exits.
     * 
     * @return settings
     */
    public Settings getSettings() {
        if (settings == null) {
            settings = new Settings();
        }

        return settings;
    }

    /**
     * Sets the application settings.
     * 
     * @param settings
     * application settings
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
