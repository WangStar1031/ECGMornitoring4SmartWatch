// Made by Wang...

package com.YHJstyle.b005.j_style_Pro.b005.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;



public class DataSend {
	HttpClient httpclient;
	HttpPost httpPost;
	static String sendingData;
	private short[] sendingBuff = new short[250 * 5 + 1];
	private short[] HeartRates = new short[6];
	private int nCounter = 0;
	public DataSend() {
	}
	public void SendDataToServer(short[] buffer, int nCount, short nHeartRate){
		int i;
		for( i = 0; i < nCount; i++){
			sendingBuff[ 250 * nCounter + i] = buffer[i];
		}
		HeartRates[nCounter] = nHeartRate;
		nCounter ++;
		if( nCounter == 5){
			nCounter = 0;
			sendingData = "HeartRate:" + Arrays.toString(HeartRates) + ";Count:" + Integer.toString(1250)+ ";Datas:" + Arrays.toString(sendingBuff);
			new HttpAsyncTask().execute("http://esl-ninja.com/apptesting/saveData.php");
		}		
	}
    public static String POST(String url, String person){
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
//            JSONObject jsonobj = new JSONObject();
//            jsonobj.put("data", sendingData);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("req", sendingData));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httpPost);
            response.getEntity().getContent();
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

        	String params = "{name:asdfdasf}";
            
            return POST(urls[0], params);
        }
        @Override
        protected void onPostExecute(String result) {
       }
    }
	 
}
