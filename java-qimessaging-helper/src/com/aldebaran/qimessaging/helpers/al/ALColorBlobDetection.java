package com.aldebaran.qimessaging.helpers.al;

import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.ALModule;

import java.util.List;

/**
 * Created by erwan on 01/05/2014.
 */
public class ALColorBlobDetection extends ALModule {

    public ALColorBlobDetection(Session session) {
        super(session);
    }

    /**
     * 100 exit                Void ()
     * Exits and unregisters the module.
     * 102 pCall               Value Value
     * NAOqi1 pCall method.
     */
    public void exit() throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("exit");
        else
            service.call("exit").get();
    }

    /**
     * 103 version             String ()
     * Returns the version of the module.
     * return: A string containing the version of the module.
     */
    public String version() throws CallError, InterruptedException {
        return (String) service.call("version").get();
    }

    /**
     * 104 ping                Bool ()
     * Just a ping. Always returns true
     * return: returns true
     */
    public Boolean ping() throws CallError, InterruptedException {
        return (Boolean) service.call("ping").get();
    }

    /**
     * 105 getMethodList       List<String> ()
     * Retrieves the module's method list.
     * return: An array of method names.
     */
    public List<String> getMethodList() throws CallError, InterruptedException {
        return (List<String>) service.call("getMethodList").get();
    }

    /**
     * 106 getMethodHelp       Value (String)
     * Retrieves a method's description.
     * methodName: The name of the method.
     * return: A structure containing the method's description.
     */
    public com.aldebaran.qimessaging.Object getMethodHelp(String param1) throws CallError, InterruptedException {
        return (com.aldebaran.qimessaging.Object) service.call("getMethodHelp", param1).get();
    }

    /**
     * 107 getModuleHelp       Value ()
     * Retrieves the module's description.
     * return: A structure describing the module.
     */
    public com.aldebaran.qimessaging.Object getModuleHelp() throws CallError, InterruptedException {
        return (com.aldebaran.qimessaging.Object) service.call("getModuleHelp").get();
    }

    /**
     * 108 wait                Bool (Int32,Int32)
     * Wait for the end of a long running method that was called using 'post'
     * id: The ID of the method that was returned when calling the method using 'post'
     * timeoutPeriod: The timeout period in ms. To wait indefinately, use a timeoutPeriod of zero.
     * return: True if the timeout period terminated. False if the method returned.
     */
    public Boolean wait(Integer param1, Integer param2) throws CallError, InterruptedException {
        return (Boolean) service.call("wait", param1, param2).get();
    }

    /**
     * 109 isRunning           Bool (Int32)
     * Returns true if the method is currently running.
     * id: The ID of the method that was returned when calling the method using 'post'
     * return: True if the method is currently running
     */
    public Boolean isRunning(Integer param1) throws CallError, InterruptedException {
        return (Boolean) service.call("isRunning", param1).get();
    }

    /**
     * 110 stop                Void (Int32)
     * returns true if the method is currently running
     * id: the ID of the method to wait for
     */
    public void stop(Integer param1) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("stop", param1);
        else
            service.call("stop", param1).get();
    }

    /**
     * 111 getBrokerName       String ()
     * Gets the name of the parent broker.
     * return: The name of the parent broker.
     */
    public String getBrokerName() throws CallError, InterruptedException {
        return (String) service.call("getBrokerName").get();
    }

    /**
     * 112 getUsage            String (String)
     * Gets the method usage string. This summarises how to use the method.
     * name: The name of the method.
     * return: A string that summarises the usage of the method.
     */
    public String getUsage(String param1) throws CallError, InterruptedException {
        return (String) service.call("getUsage", param1).get();
    }

    /**
     * 113 subscribe           Void (String,Int32,Float)
     * Subscribes to the extractor. This causes the extractor to start writing information to memory using the keys described by getOutputNames(). These can be accessed in memory using ALMemory.getData("keyName"). In many cases you can avoid calling subscribe on the extractor by just calling ALMemory.subscribeToEvent() supplying a callback method. This will automatically subscribe to the extractor for you.
     * name: Name of the module which subscribes.
     * period: Refresh period (in milliseconds) if relevant.
     * precision: Precision of the extractor if relevant.
     */
    public void subscribe(String param1, Integer param2, Float param3) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("subscribe", param1, param2, param3);
        else
            service.call("subscribe", param1, param2, param3).get();
    }

    /**
     * 114 subscribe           Void (String)
     * Subscribes to the extractor. This causes the extractor to start writing information to memory using the keys described by getOutputNames(). These can be accessed in memory using ALMemory.getData("keyName"). In many cases you can avoid calling subscribe on the extractor by just calling ALMemory.subscribeToEvent() supplying a callback method. This will automatically subscribe to the extractor for you.
     * name: Name of the module which subscribes.
     */
    public void subscribe(String param1) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("subscribe", param1);
        else
            service.call("subscribe", param1).get();
    }

    /**
     * 115 unsubscribe         Void (String)
     * Unsubscribes from the extractor.
     * name: Name of the module which had subscribed.
     */
    public void unsubscribe(String param1) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("unsubscribe", param1);
        else
            service.call("unsubscribe", param1).get();
    }

    /**
     * 116 updatePeriod        Void (String,Int32)
     * Updates the period if relevant.
     * name: Name of the module which has subscribed.
     * period: Refresh period (in milliseconds).
     */
    public void updatePeriod(String param1, Integer param2) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("updatePeriod", param1, param2);
        else
            service.call("updatePeriod", param1, param2).get();
    }

    /**
     * 117 updatePrecision     Void (String,Float)
     * Updates the precision if relevant.
     * name: Name of the module which has subscribed.
     * precision: Precision of the extractor.
     */
    public void updatePrecision(String param1, Float param2) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("updatePrecision", param1, param2);
        else
            service.call("updatePrecision", param1, param2).get();
    }

    /**
     * 118 getCurrentPeriod    Int32 ()
     * Gets the current period.
     * return: Refresh period (in milliseconds).
     */
    public Integer getCurrentPeriod() throws CallError, InterruptedException {
        return (Integer) service.call("getCurrentPeriod").get();
    }

    /**
     * 119 getCurrentPrecision Float ()
     * Gets the current precision.
     * return: Precision of the extractor.
     */
    public Float getCurrentPrecision() throws CallError, InterruptedException {
        return (Float) service.call("getCurrentPrecision").get();
    }

    /**
     * 120 getMyPeriod         Int32 (String)
     * Gets the period for a specific subscription.
     * name: Name of the module which has subscribed.
     * return: Refresh period (in milliseconds).
     */
    public Integer getMyPeriod(String param1) throws CallError, InterruptedException {
        return (Integer) service.call("getMyPeriod", param1).get();
    }

    /**
     * 121 getMyPrecision      Float (String)
     * Gets the precision for a specific subscription.
     * name: name of the module which has subscribed
     * return: precision of the extractor
     */
    public Float getMyPrecision(String param1) throws CallError, InterruptedException {
        return (Float) service.call("getMyPrecision", param1).get();
    }

    /**
     * 122 getSubscribersInfo  Value ()
     * Gets the parameters given by the module.
     * return: Array of names and parameters of all subscribers.
     */
    public com.aldebaran.qimessaging.Object getSubscribersInfo() throws CallError, InterruptedException {
        return (com.aldebaran.qimessaging.Object) service.call("getSubscribersInfo").get();
    }

    /**
     * 123 getOutputNames      List<String> ()
     * Get the list of values updated in ALMemory.
     * return: Array of values updated by this extractor in ALMemory
     */
    public List<String> getOutputNames() throws CallError, InterruptedException {
        return (List<String>) service.call("getOutputNames").get();
    }

    /**
     * 124 getEventList        List<String> ()
     * Get the list of events updated in ALMemory.
     * return: Array of events updated by this extractor in ALMemory
     */
    public List<String> getEventList() throws CallError, InterruptedException {
        return (List<String>) service.call("getEventList").get();
    }

    /**
     * 125 getMemoryKeyList    List<String> ()
     * Get the list of events updated in ALMemory.
     * return: Array of events updated by this extractor in ALMemory
     */
    public List<String> getMemoryKeyList() throws CallError, InterruptedException {
        return (List<String>) service.call("getMemoryKeyList").get();
    }

    /**
     * 126 setFrameRate        Bool (String,Int32)
     * Sets the extractor framerate for a chosen subscriber
     * subscriberName: Name of the subcriber
     * framerate: New framerate
     * return: True if the update succeeded, False if not
     */
    public Boolean setFrameRate(String param1, Integer param2) throws CallError, InterruptedException {
        return (Boolean) service.call("setFrameRate", param1, param2).get();
    }

    /**
     * 127 setFrameRate        Bool (Int32)
     * Sets the extractor framerate for all the subscribers
     * framerate: New framerate
     * return: True if the update succeeded, False if not
     */
    public Boolean setFrameRate(Integer param1) throws CallError, InterruptedException {
        return (Boolean) service.call("setFrameRate", param1).get();
    }

    /**
     * 128 setResolution       Bool (Int32)
     * Sets extractor resolution
     * resolution: New resolution
     * return: True if the update succeeded, False if not
     */
    public Boolean setResolution(Integer param1) throws CallError, InterruptedException {
        return (Boolean) service.call("setResolution", param1).get();
    }

    /**
     * 129 setActiveCamera     Bool (Int32)
     * Sets extractor active camera
     * cameraId: Id of the camera that will become the active camera
     * return: True if the update succeeded, False if not
     */
    public Boolean setActiveCamera(Integer param1) throws CallError, InterruptedException {
        return (Boolean) service.call("setActiveCamera", param1).get();
    }

    /**
     * 130 setParameter        Void (String,Value)
     * DEPRECATED: Sets pause and resolution
     * paramName: Name of the parameter to set
     * value: New value
     */
    public void setParameter(String param1, com.aldebaran.qimessaging.Object param2) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("setParameter", param1, param2);
        else
            service.call("setParameter", param1, param2).get();
    }

    /**
     * 131 getFrameRate        Int32 ()
     * Gets extractor framerate
     * return: Current value of the framerate of the extractor
     */
    public Integer getFrameRate() throws CallError, InterruptedException {
        return (Integer) service.call("getFrameRate").get();
    }

    /**
     * 132 getResolution       Int32 ()
     * Gets extractor resolution
     * return: Current value of the resolution of the extractor
     */
    public Integer getResolution() throws CallError, InterruptedException {
        return (Integer) service.call("getResolution").get();
    }

    /**
     * 133 getActiveCamera     Int32 ()
     * Gets extractor active camera
     * return: Id of the current active camera of the extractor
     */
    public Integer getActiveCamera() throws CallError, InterruptedException {
        return (Integer) service.call("getActiveCamera").get();
    }

    /**
     * 134 isPaused            Bool ()
     * Gets extractor pause status
     * return: True if the extractor is paused, False if not
     */
    public Boolean isPaused() throws CallError, InterruptedException {
        return (Boolean) service.call("isPaused").get();
    }

    /**
     * 135 isProcessing        Bool ()
     * Gets extractor running status
     * return: True if the extractor is currently processing images, False if not
     */
    public Boolean isProcessing() throws CallError, InterruptedException {
        return (Boolean) service.call("isProcessing").get();
    }

    /**
     * 136 pause               Void (Bool)
     * Changes the pause status of the extractor
     * paused: New pause satus
     */
    public void pause(Boolean param1) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("pause", param1);
        else
            service.call("pause", param1).get();
    }

    /**
     * 138 setColor            Void (Int32,Int32,Int32,Int32)
     * Color parameter setting
     * r: The R component in RGB of the color to find
     * g: The G component in RGB of the color to find
     * b: The B component in RGB of the color to find
     * colorThres: The color threshold
     */
    public void setColor(Integer param1, Integer param2, Integer param3, Integer param4) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("setColor", param1, param2, param3, param4);
        else
            service.call("setColor", param1, param2, param3, param4).get();
    }

    /**
     * 139 setObjectProperties Void (Int32,Float)
     * Object parameter setting
     * minSize: The minimum size of the cluster to find
     * span: The span of the object in meters
     */
    public void setObjectProperties(Integer param1, Float param2) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("setObjectProperties", param1, param2);
        else
            service.call("setObjectProperties", param1, param2).get();
    }

    /**
     * 140 setObjectProperties Void (Int32,Float,String)
     * Object parameter setting
     * minSize: The minimum size of the cluster to find
     * span: The span of the object in meters
     * shape: The shape of the object
     */
    public void setObjectProperties(Integer param1, Float param2, String param3) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("setObjectProperties", param1, param2, param3);
        else
            service.call("setObjectProperties", param1, param2, param3).get();
    }

    /**
     * 141 getCircle           Value ()
     * Send back the x,y,radius of the circle if any
     * return: The circle as x,y,radius in image relative coordinates (x,radius divided by rows and y by cols)
     */
    public com.aldebaran.qimessaging.Object getCircle() throws CallError, InterruptedException {
        return (com.aldebaran.qimessaging.Object) service.call("getCircle").get();
    }

    /**
     * 142 getAutoExposure     Bool ()
     * Get the camera auto exposure mode
     * return: A flag saying the exposure is auto or not
     */
    public Boolean getAutoExposure() throws CallError, InterruptedException {
        return (Boolean) service.call("getAutoExposure").get();
    }

    /**
     * 143 setAutoExposure     Void (Bool)
     * Set the camera auto exposure to on
     * mode: Whether the exposure is auto or not
     * * Signals:
     */
    public void setAutoExposure(Boolean param1) throws CallError, InterruptedException {
        if (isAsynchronous)
            service.call("setAutoExposure", param1);
        else
            service.call("setAutoExposure", param1).get();
    }


}
    