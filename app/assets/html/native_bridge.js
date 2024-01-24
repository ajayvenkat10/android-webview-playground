function closeFlutterView() {
    try {
        NativeFlutterInterface.closeFlutterView() ;
    }
    catch (e) {
        console.log(`NativeFlutterInterface not found !`);
    }
}

function provideHeartBeatCallback() {
    try{
        NativeFlutterInterface.provideHeartBeatCallback();
    }
    catch (e) {
        console.log(`NativeFlutterInterface not found !`);
    }
}

function getOnboardingParams(){
    let result = "{}";
        try {
            result = NativeFlutterInterface.getRequestHeaders() ;
        }
        catch (e) {
            console.log(`NativeFlutterInterface not found !`);
        }
        return result ;
}