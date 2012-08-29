Remoteroid web service
=========
  
![Remoteroid](https://raw.github.com/kunny/RemoteroidWeb/master/remoteroid_logo.png)
  
This project aims to support 'Remote-connect' feature, which user can connect to the phone from PC, without any control on the phone.

License
------------------
Copyright(c) 2012 Taeho Kim (jyte82@gmail.com)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

System Architecture
-----------------
* **Google App Engine** for Web service
* **JAX-RS Jersey** for REST service
* **Google Cloud Messaging** for Push message service


REST API documentation
-----------------
**Path** : /apis  

*Remoteroid Web Service REST API provides various method regarding Account, Device, and Administration. APIs in Account and Device category is for service users, while Admin is only for service administrator. (Note that after you completed setup by accessing `http://[your-application-id].appspot.com/apis/admin/init`, you should remove API Path for Admin to prevent illegal access from anonymous.)*  


### Account
-----------------
**Path** : /apis/account  

*Provides method to user that can register, login, and unregister from this service.*

#### Add account

*Adds new user account. Each user is identified by its own E-mail address, so an E-mail address should not be duplicated.*

* **Method** : POST
* **Path** : /apis/account/register
* **Content type** : application/json
* **Request payload** : `Account` represented in JSON format containing user's E-mail address and raw password, in JSON format.

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/account/register
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"email" : "android@android.com", "password" : "test"}

**Returns**  
**User registration succeed :**
>  {"result":"0","data":{"@type":"account","email":"android@android.com","password":"a94a8fe5ccb19ba61c4c873d391e987982fbbd3"}}

**E-mail duplicates :**
> {"result" : "-1", "errorCode" : "256"}

**E-mail format is not valid :**
> {"result" : "-1", "errorCode" : "258"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}

#### Login

*Process user's login. Once authenticated, user will retrieve user's account data with SHA-hashed password.*

* **Method** : POST
* **Path** : /apis/account/login
* **Content type** : application/json
* **Request payload** : `Account` represented in JSON format containing user's E-mail address and raw password, in JSON format

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/account/register
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"email" : "android@android.com", "password" : "test"}

**Returns**    
**Login succeed :**
>  {"result":"0","data":{"@type":"account","email":"android@android.com","password":"a94a8fe5ccb19ba61c4c873d391e987982fbbd3"}}

**Authentication failed :**
>  {"result":"-1","errorCode":"257"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}

#### Delete account

*Remove user's account, with its own devices.*

* **Method** : POST
* **Path** : /apis/account/unregister
* **Content type** : application/json
* **Request payload** : `Account` represented in JSON format containing user's E-mail address and SHA-hashed password which is obtained in register/login procedure, in JSON format.

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/account/unregister
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"email" : "android@android.com", "password" : "a94a8fe5ccb19ba61c4c873d391e987982fbbd3"}

**Returns**  
**Deleted account :**
>  {"result" : "0"}

**Authentication failed :**
>  {"result":"-1","errorCode":"257"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}

### Device
-----------------
**Path** : /apis/device   

*Provides method to user that can add, update and delete devices linked to user.*

#### Add new device

*Register a new device. Payload should contain device information to be registered. Once device information has registered, each device's UUID is generated and returned to user with response. User should store device's UUID to request device-specific operations.*  

* **Method** : POST
* **Path** : /apis/device/register
* **Content type** : application/json
* **Request payload** : `Device` info containing device nickname, owner's account information and GCM registration key, in JSON format

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/device/register
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"nickname":"Nexus","ownerAccount":{"email":"android@android.com","password":"a94a8fe5ccb19ba61c4c873d391e987982fbbd3"},"registrationKey":"regkey"}

**Returns**  
**Registered device :**
>  {"result":"0","data":{"@type":"device","deviceUUID":"3c2925e3-4b8a-4dbd-9aaf-c315d0fa6b1e","nickname":"Nexus","ownerAccount":{"email":"android@android.com","password":"a94a8fe5ccb19ba61c4c873d391e987982fbbd3"},"registrationKey":"regkey"}}

**Authentication failed :**
>  {"result":"-1","errorCode":"257"}

**Device nickname duplicates :**
>  {"result":"-1","errorCode":"512"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}

#### Get my device list

*Retrieve my device list*  

* **Method** : POST
* **Path** : /apis/device/list
* **Content type** : application/json
* **Request payload** : User's Account information in JSON format

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/device/list
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"email":"android@android.com","password":"a94a8fe5ccb19ba61c4c873d391e987982fbbd3"}  

**Returns**  
**My device list (two or more devices):**
> {"result":"0","data":[{"deviceUUID":"6b905046-caf5-4ebb-8872-66a2aace01de","nickname":"Nexus","registrationKey":"regkey"},{"deviceUUID":"6b905046-caf5-3ecc-8872-55a2aace10fa","nickname":"Galaxy","registrationKey":"regkey2"}]}

**My device list (only one device):**
> {"result":"0","data":{"deviceUUID":"6b905046-caf5-4ebb-8872-66a2aace01de","nickname":"Nexus","registrationKey":"regkey"}}

**Authentication failed :**
>  {"result":"-1","errorCode":"257"}

**No device exists :**
>  {"result":"-1","errorCode":"513"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}  

#### Update device information

*Updates device information.*  

* **Method** : POST
* **Path** : /apis/device/update
* **Content type** : application/json
* **Request payload** : `Device` information to be updated, in JSON format. Note that only nickname and GCM registration key is subject to change.

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/device/update
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"deviceUUID":"3c2925e3-4b8a-4dbd-9aaf-c315d0fa6b1e","nickname":"Nexus","ownerAccount":{"email":"android@android.com","password":"a94a8fe5ccb19ba61c4c873d391e987982fbbd3"},"registrationKey":"New reg key"}

**Returns**  
**Updated device information :**
>  {"result":"0"}

**Authentication failed :**
>  {"result":"-1","errorCode":"257"}

**Device nickname duplicates :**
>  {"result":"-1","errorCode":"512"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}

#### Delete device

*Delete registered device.*  

* **Method** : POST
* **Path** : /apis/device/delete
* **Content type** : application/json
* **Request payload** : `Device` info containing device nickname, owner's account information and GCM registration key, in JSON format

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/device/delete
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"deviceUUID":"3c2925e3-4b8a-4dbd-9aaf-c315d0fa6b1e","nickname":"Nexus","ownerAccount":{"email":"android@android.com","password":"a94a8fe5ccb19ba61c4c873d391e987982fbbd3"},"registrationKey":"Regkey"}

**Returns**  
**Deleted device :**
>  {"result":"0"}

**Authentication failed :**
>  {"result":"-1","errorCode":"257"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}

#### Delete all device linked to user

*Delete all device linked to this user.*  

* **Method** : POST
* **Path** : /apis/device/deleteAll
* **Content type** : application/json
* **Request payload** : `Account` information represented in JSON format

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/device/deleteAll
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"email" : "android@android.com", "password" : "a94a8fe5ccb19ba61c4c873d391e987982fbbd3"}

**Returns**  
**Deleted all device :**
>  {"result":"0"}

**Authentication failed :**
>  {"result":"-1","errorCode":"257"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}

#### Request connection request

*Send connection request message to selected device to make device can connect to server by itself*  

* **Method** : POST
* **Path** : /apis/device/wakeup
* **Content type** : application/json
* **Request payload** : `WakeupMessage` info containing device information and server's IP address, in JSON format

**Sample request**
> * Request URI : http://[YOUR_APPLICATION_ID].appspot.com/apis/device/wakeup
> * Request Method : POST
> * Content type : application/json
> * Request payload : {"device":{"deviceUUID":"3c2925e3-4b8a-4dbd-9aaf-c315d0fa6b1e","nickname":"Nexus","ownerAccount":{"email":"android@android.com","password":"a94a8fe5ccb19ba61c4c873d391e987982fbbd3"},"registrationKey":"Regkey"},"serverIpAddress":"210.1.1.1"}

**Returns**  
**Message sent :**
>  {"result":"0"}

**Authentication failed :**
>  {"result":"-1","errorCode":"257"}

**Unhandled error occurred :**
> {"result" : "-1", "errorCode" : "0"}

### Admin
-----------------
**Path** : /apis/admin   

*Provides method for administration purpose.*

#### Initiate Datastore for GCM API Key

*YOU SHOULD REMOVE THIS REST API PATH AFTER SETUP PROCEDURE HAS DONE.*  

* **Method** : GET
* **Path** : /apis/admin/init
* **Content type** : none
* **Request payload** : none

**Sample request - Just type following URI on your web browser.**
> * Request URI : http://[your-application-id].appspot.com/apis/admin/init

**Result**  
**Redirected to `configuration_done.html`**

Reponse Codes from REST APIs
-----------------
### Result
* Description : Success
* JSON element :  
**result** : 0  

### Error  
#### General  
* Description : Unspecified error has occurred.
* JSON elements :  
**result** : -1  
**errorCode** : 0  

#### Account
##### Duplicated E-mail
* Description : There is duplicated e-mail exists in the user list.
* JSON elements :  
**result** : -1  
**errorCode**  : 256(0x100)  
* See Also :  
[Add account](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#add-account)

##### E-mail format is not valid
* Description : The E-mail address that has provided is not valid.
* JSON elements :  
**result** : -1  
**errorCode**  : 258(0x102)  
* See Also :  
[Add account](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#add-account)

##### Authentication failed
* Description : User authentication has failed.
* JSON elements :  
**result** : -1  
**errorCode**  : 257(0x101)
* See Also :  
[Login](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#login)  
[Delete account](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#delete-account)  
[Add new device](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#add-new-device)  
[Update device information](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#update-device-information)  
[Delete device](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#delete-device)  
[Delete all device linked to user](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#delete-all-device-linked-to-user)  

#### Device
##### Duplicicated device name
* Description : There is duplicicated device name exists on user's account.
* JSON elements :  
**result** : -1  
**errorCode**  : 512(0x200)
* See Also :  
[Add new device](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#add-new-device)  
[Update device information](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#update-device-information)    

##### Device not found
* Description : Requested device has not found on the user's device list.
* JSON elements :  
**result** : -1  
**errorCode**  : 513(0x201)  
* Seel Also :  
[Update device information](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#update-device-information)  
[Delete device](https://github.com/kunny/RemoteroidWeb/edit/master/README.md#delete-device)  


About Remoteroid
------------------
Remoteroid is a remote control solution for Android platform, including features like Drag-and-drop file transfer and delivering phone's notifications(Incoming call, Message, Notifications from apps, etc).

You can control your Phone on your PC without leaving your hands from keyboard and mouse.

Stable version of Remoteroid is available on Google code, but It only supports Windows. In addition, by now, **Remoteroid requires root permission** on the device. We're finding another way to run Remoteroid that doesn't requires root permission. Please stay tuned for new releases. And if you have any idea about 'Non-rooting approach', please contact to jyte82@gmail.com.

* [Remoteroid on Google code (Stable, Only supports Windows)](http://remoteroid.googlecode.com)
* [Remoteroid server, platform-independant (In development)](https://github.com/kunny/RemoteroidServerUniversal)
