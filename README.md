# VizAssist
An Android app using Cloud OCR to assist text reading tasks for users with vision impairment.


### The purpose
To increase accessibility.
* Blind people are facing many visual challenges everyday, includes:
    * Identification
    * Description
    * Reading
    * Other

![](user_interface.png)

## How it work (client-side)
![](recognition_result.png)

* **First page**
    * Camera capture button
    * Image gallery button (upload from local)
    * Test and image placeholder

* **Result page**
    * Image captured or selected
    * Result and error message

* **What happen in between**
    * Image data accessing and packaging
    * OCR server query with image data
    * UI update with server query result

## How it work (server-side)
* Utilize an **IaaS** service model in Google Compute Engine
* build an docker container with Tomcat image to run our server-side application in GCE Virtual Machine

