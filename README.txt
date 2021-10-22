Author: Nitish
Date: 10th Feb 2019

************ Issue with Chrome ******************

While using Chrome If chromedriver is launching the chrome with Settings page then 
follow below steps to overcome this:
1. Go to Run (Windows key + R)
2. Type regedit and press Enter
3. This will open the Registory Editor
4. Then go to the path HKEY_CURRENT_USER\Software\Google\Chrome\TriggeredReset by clicking on Expand button besides each folder
5. Then delete the TriggeredReset and close the Registory editor 