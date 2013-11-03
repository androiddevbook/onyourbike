import com.androiddevbook.onyourbike.chapter10.model.TimerState;

/**
 * TimerStateTestable
 * 
 * Testable TimerState class for the "On Your Bike" application.
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
public class TimerStateTestable extends TimerState {

    public TimerStateTestable() {
        super();
        time = new SettableTime();
    }

    public void setElapsedTime(long value) {
        ((SettableTime) time).time = value;
    }
}
