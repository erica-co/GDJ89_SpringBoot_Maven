
export function setHeaders() {

    const headers = new Headers();
    headers.append("Authorization", "Bearer "+ sessionStorage.getItem("AccessToken"))
    headers.append("RefreshToken", localStorage.getItem("RefreshToken"))




    return headers;

}

export function getHeaders(res) {
    //console.log(r.headers.get("AccessToken")); 
     
    let t = r.headers.get("AccessToken") ;
    if(t != null || t !=''){
    sessionStorage.setItem("AccessToken",t)
    }

}