<div id="loader" style="position:fixed;top:0;left:0;height: 100vh;width: 100%;
                 background-color: rgba(0,0,0,0.2);z-index: 1000; display: none;">
                <center><button id="state" style="margin-top: 45vh;font-size: 1.1em;
                   font-weight: bold;font-family: noto san;color:#fff; 
                   background-color:#427AA1; padding: 10px;
                   border:none;">wait request is processing...</button></center>
            </div>
            <div id="alert" style="position:fixed;top:0;left:0;height: 100vh;width: 100%;
                 background-color: rgba(0,0,0,0.2);z-index: 1000; display: none;">
                <center>
                <div id="alertbox" style="width: 340px; background-color: #fff; 
                      border:1px solid #eee; text-align: center;padding: 20px 5px;
                      margin-top: 28vh">
                    
                    <p id="message" style=" font-family: noto-sans;
                       margin-bottom: 50px;">message goes here</p>
                    
                    <button onclick="document.getElementById('alert').style.display='none'">ok</button>
                    
                </div>
                </center>
                
            </div>