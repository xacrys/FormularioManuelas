package ec.gob.stptv.formularioManuelas.controlador.sincronizacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import ec.gob.stptv.formularioManuelas.controlador.util.Utilitarios;

public class WebService {

	public static Bitmap downloadImage(String url) {
		Bitmap bitmap = null;
		InputStream input_stream = null;
		try {
			input_stream = openHttpConnection(url);
			if(input_stream != null)
			{
				bitmap = BitmapFactory.decodeStream(input_stream);
				input_stream.close();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return bitmap;
	}

	public static InputStream openHttpConnection(String url) {
		InputStream input_stream = null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();

			HttpGet method = new HttpGet(url);

			HttpResponse httpResponse = httpclient.execute(method);
			input_stream = httpResponse.getEntity().getContent();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return input_stream;
	}

	public static String getJsonData(String url) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnection(url);
			response = convertStreamToString(input_stream);

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return response;
	}

	public static String getJsonData(String url, String values, DefaultHttpClient httpClient) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionPost(url, values, httpClient);
			if(input_stream != null)
			{
				response = convertStreamToString(input_stream);
			}
			else
			{
				response = "";
			}


		} catch (Exception exception) {
			exception.printStackTrace();
			response = "";
		}

		return response;
	}
	
	public static String getJsonData(String url, JSONObject values, DefaultHttpClient httpClient) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionPost(url, values, httpClient);
			if(input_stream != null)
			{
				response = convertStreamToString(input_stream);
			}
			else
			{
				response = "";
			}


		} catch (Exception exception) {
			exception.printStackTrace();
			response = "";
		}

		return response;
	}
	
	public static String getData(String url, JSONObject values, DefaultHttpClient httpClient) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionPostData(url, values, httpClient);
			if(input_stream != null)
			{
				response = convertStreamToString(input_stream);
			}
			else
			{
				response = "";
			}


		} catch (Exception exception) {
			exception.printStackTrace();
			response = "";
		}

		return response;
	}
	
	public static String getJsonData(String url, DefaultHttpClient httpClient) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionGet(url, httpClient);
			response = convertStreamToString(input_stream);

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return response;
	}
	
	public static String getJsonData(String url, JSONObject values) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionPost(url, values);
			if(input_stream != null)
			{
				response = convertStreamToString(input_stream);
			}
			else
			{
				response = "";
			}


		} catch (Exception exception) {
			exception.printStackTrace();
			response = "";
		}

		return response;
	}
	
	public static String sendJson(String url, String json) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionPost(url, json);
			if(input_stream != null)
			{
			response = convertStreamToString(input_stream);
			}


		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return response;
	}
	
	public static String sendJson(String url, String json, DefaultHttpClient httpClient) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionPost(url, json, httpClient);
			if(input_stream != null)
			{
			response = convertStreamToString(input_stream);
			}


		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return response;
	}
	
	
	public static String getJsonData(String url, ArrayList<Parameter> paremeters) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionPost(url, paremeters);
			if(input_stream != null)
			{
			response = convertStreamToString(input_stream);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return response;
	}
	
	public static String sendJsonCompres(String url, String json, DefaultHttpClient httpClient) {
		String response = "";
		try {
			InputStream input_stream = openHttpConnectionPostCompress(url, json, httpClient);
			if(input_stream != null)
			{
			response = convertStreamToString(input_stream);
			}


		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return response;
	}

	public static String convertStreamToString(InputStream input_stream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				input_stream));
		StringBuilder string_builder = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				//string_builder.append(line + "\n");
				string_builder.append(line);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			try {
				input_stream.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return string_builder.toString();
	}

	public static InputStream openHttpConnectionPost(String url,
			ArrayList<Parameter> paremeters) {
		InputStream input_stream = null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost method = new HttpPost(url);

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (Parameter parameter : paremeters) {

				nameValuePairs.add(new BasicNameValuePair(parameter.getKey(),
						parameter.getValue()));

			}
			method.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse httpResponse = httpclient.execute(method);
			input_stream = httpResponse.getEntity().getContent();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return input_stream;
	}
	
	
	public static boolean isURLReachable(String _url)
	{
		Utilitarios.logInfo(WebService.class.getName(), "Entra al ping ");
		
		try {
			URL url = new URL(_url);
			HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
			urlc.setRequestMethod("HEAD");
			urlc.setConnectTimeout(5*1000); //5sg
			//urlc.connect();
			
			if(urlc.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				Utilitarios.logInfo(WebService.class.getName(), "Entra al ping urlc.getResponseCode() " + urlc.getResponseCode());
				return true;
			}
			else
			{
				Utilitarios.logInfo(WebService.class.getName(), "Entra al ping urlc.getResponseCode() " + urlc.getResponseCode());
				return false;
			}
		} 
		
		catch (Exception e) {
			return false;
		}
		
	}
	public static InputStream openHttpConnectionPost(String url,
			JSONObject dato) {
		
		InputStream inputStream = null;
			try
	        {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				
				HttpParams httpParams = new BasicHttpParams();
				int timeoutConnection = 60000;
				HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
				httpClient.setParams(httpParams);
				
				HttpPost post = new HttpPost(url);
				post.setHeader("content-type", "application/json");
				post.setHeader("accept", "application/json"); // La respuesta del servidor en formato JSON

				StringEntity entity = new StringEntity(dato.toString(), HTTP.UTF_8);
				post.setEntity(entity);
				
	        	HttpResponse httpResponse = httpClient.execute(post);
	        	
				 final int statusCode = httpResponse.getStatusLine().getStatusCode();
				 Utilitarios.logInfo(WebService.class.getName(), "ESTADO HTTP"+ statusCode + " for URL "+ url);
				 
	            if (statusCode != HttpStatus.SC_OK) {
	            	Utilitarios.logError(WebService.class.getName(), "ESTADO HTTP" + "Error " + statusCode + " para URL "+ url);
	                return null;
	            }
	            
	            HttpEntity httpEntity = httpResponse.getEntity(); // la respuesta la trae en tipo httpEntity
	            if (httpEntity != null) {
	            	Utilitarios.logInfo(WebService.class.getName(), "Servidor Conexion establecida");
	                inputStream = httpEntity.getContent();
	                
	            } else {
	            	inputStream = null;
	            }
	        }

			catch (ClientProtocolException e) {
				Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo ");
				
				e.printStackTrace();
			} catch (IOException e) {
				
				Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
				
				inputStream = null;
				e.printStackTrace();
			}

		return inputStream;
	}
	
	/*public static void abortHttpRequest() {
		httpClient.getConnectionManager().shutdown();
	}*/
	
	public static InputStream openHttpConnectionPost(String url, JSONObject dato, DefaultHttpClient httpClient) {
		
		InputStream inputStream = null;
			try
	        {
				HttpParams httpParams = new BasicHttpParams();
				int timeoutConnection = 60000;
				HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
				httpClient.setParams(httpParams);
				
				HttpPost post = new HttpPost(url);
				post.setHeader("content-type", "application/json");
				post.setHeader("accept", "application/json"); // La respuesta del servidor en formato JSON
				
				
				
				StringEntity entity = new StringEntity(dato.toString(), HTTP.UTF_8);
				Utilitarios.logInfo(WebService.class.getName(), "Hogar Entity: "+ entity.toString());
				
				post.setEntity(entity);
	        	HttpResponse httpResponse = httpClient.execute(post);
	        	
				 final int statusCode = httpResponse.getStatusLine().getStatusCode();
				 Utilitarios.logInfo(WebService.class.getName(), "ESTADO HTTP"+ statusCode + " for URL "+ url);
				 
	            if (statusCode != HttpStatus.SC_OK) {
	            	Utilitarios.logError(WebService.class.getName(), "ESTADO HTTP" + "Error " + statusCode + " para URL "+ url);
	                return null;
	            }
	            
	            HttpEntity httpEntity = httpResponse.getEntity(); // la respuesta la trae en tipo httpEntity
	            if (httpEntity != null) {
	            	Utilitarios.logInfo(WebService.class.getName(), "Servidor Conexión establecida");
	                inputStream = httpEntity.getContent();
	                
	            } else {
	            	inputStream = null;
	            }
	        
	        }

			catch (ClientProtocolException e) {
				
				Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo ");
				
				e.printStackTrace();
			} catch (IOException e) {
				
				Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
				
				inputStream = null;
				e.printStackTrace();
			}
		return inputStream;
	}
	
	public static InputStream openHttpConnectionPostData(String url, JSONObject dato, DefaultHttpClient httpClient) {
		
		InputStream inputStream = null;
			try
	        {
				HttpParams httpParams = new BasicHttpParams();
				int timeoutConnection = 60000;
				HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
				httpClient.setParams(httpParams);
				
				HttpPost post = new HttpPost(url);
				post.setHeader("content-type", "application/json");
				
				
				StringEntity entity = new StringEntity(dato.toString(), HTTP.UTF_8);
				Utilitarios.logInfo(WebService.class.getName(), "Hogar Entity: "+ entity.toString());
				
				post.setEntity(entity);
	        	HttpResponse httpResponse = httpClient.execute(post);
	        	
				 final int statusCode = httpResponse.getStatusLine().getStatusCode();
				 Utilitarios.logInfo(WebService.class.getName(), "ESTADO HTTP"+ statusCode + " for URL "+ url);
				 
	            if (statusCode != HttpStatus.SC_OK) {
	            	Utilitarios.logError(WebService.class.getName(), "ESTADO HTTP" + "Error " + statusCode + " para URL "+ url);
	                return null;
	            }
	            
	            HttpEntity httpEntity = httpResponse.getEntity(); // la respuesta la trae en tipo httpEntity
	            if (httpEntity != null) {
	            	Utilitarios.logInfo(WebService.class.getName(), "Servidor Conexión establecida");
	                inputStream = httpEntity.getContent();
	                
	            } else {
	            	inputStream = null;
	            }
	        
	        }

			catch (ClientProtocolException e) {
				
				Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo ");
				
				e.printStackTrace();
			} catch (IOException e) {
				
				Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
				
				inputStream = null;
				e.printStackTrace();
			}
		return inputStream;
	}
	
	public static InputStream openHttpConnectionPost(String url,
			String json) {
		
		InputStream inputStream = null;
		
		try
        {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			HttpParams httpParams = new BasicHttpParams();
			int timeoutConnection = 60000;
			HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
			httpClient.setParams(httpParams);
			
			
			HttpPost post = new HttpPost(url);
			post.setHeader("content-type", "application/json");
			//httpClient.set
			//post.setHeader("accept", "application/json"); // La respuesta del servidor en formato JSON
	
			StringEntity entity = new StringEntity(json, HTTP.UTF_8);
			Utilitarios.logInfo(WebService.class.getName(),"Vivienda Entity: "+ entity.toString());
			
			post.setEntity(entity);
			
        	HttpResponse httpResponse = httpClient.execute(post);
        	

			 final int statusCode = httpResponse.getStatusLine().getStatusCode();
			 Utilitarios.logInfo(WebService.class.getName(), "ESTADO HTTP" + statusCode + " for URL "+ url);
			 
            if (statusCode != HttpStatus.SC_OK) {
            	Utilitarios.logError(WebService.class.getName(), "ESTADO HTTP" + "Error " + statusCode + " para URL "+ url);
                return null;
            }
            
            HttpEntity httpEntity = httpResponse.getEntity(); // la respuesta la trae en tipo httpEntity
            if (httpEntity != null) {
            	Utilitarios.logInfo(WebService.class.getName(), "Servidor Conexión establecida");
                inputStream = httpEntity.getContent();
                
            } else {
            	inputStream = null;
            }
        
        } 

		catch (ClientProtocolException e) {
			Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
			e.printStackTrace();
		} catch (IOException e) {
			Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
			inputStream = null;
			e.printStackTrace();
		}
		return inputStream;
	}
	
	public static InputStream openHttpConnectionPost(String url, String json, DefaultHttpClient httpClient) {
		
		InputStream inputStream = null;
		
		try
        {    
			StringEntity entity = new StringEntity(json, HTTP.UTF_8);
			
			HttpParams httpParams = new BasicHttpParams();
			int timeoutConnection = 60000;
			HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
			httpClient.setParams(httpParams);
			
		
			HttpPost post = new HttpPost(url);
			post.setHeader("content-type", "application/json");
			post.setEntity(entity);

        	HttpResponse httpResponse = httpClient.execute(post);
        	

			 final int statusCode = httpResponse.getStatusLine().getStatusCode();
			 Utilitarios.logInfo(WebService.class.getName(), "ESTADO HTTP" + statusCode + " for URL "+ url);
			 
            if (statusCode != HttpStatus.SC_OK) {
            	Utilitarios.logError(WebService.class.getName(), "ESTADO HTTP" + "Error " + statusCode + " para URL "+ url);
                return null;
            }
            
            HttpEntity httpEntity = httpResponse.getEntity(); // la respuesta la trae en tipo httpEntity
            if (httpEntity != null) {
            	Utilitarios.logInfo(WebService.class.getName(), "Servidor Conexión establecida");
                inputStream = httpEntity.getContent();
                
            } else {
            	inputStream = null;
            }
        
        } 

		catch (ClientProtocolException e) {
			Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
			e.printStackTrace();
		} catch (IOException e) {
			Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
			inputStream = null;
			e.printStackTrace();
		}
		return inputStream;
	}
	 public static InputStream openHttpConnectionGet(String url, DefaultHttpClient httpClient) {
			
			InputStream inputStream = null;
				try
		        {
					HttpParams httpParams = new BasicHttpParams();
					int timeoutConnection = 60000;
					HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
					httpClient.setParams(httpParams);
					
					HttpGet get = new HttpGet(url);
					get.setHeader("accept", "application/json"); // La respuesta del servidor en formato JSON
					
		        	HttpResponse httpResponse = httpClient.execute(get);
		        	
					 final int statusCode = httpResponse.getStatusLine().getStatusCode();
					 Utilitarios.logInfo(WebService.class.getName(), "ESTADO HTTP"+ statusCode + " for URL "+ url);
					 
		            if (statusCode != HttpStatus.SC_OK) {
		            	Utilitarios.logError(WebService.class.getName(), "ESTADO HTTP" + "Error " + statusCode + " para URL "+ url);
		                return null;
		            }
		            
		            HttpEntity httpEntity = httpResponse.getEntity(); // la respuesta la trae en tipo httpEntity
		            if (httpEntity != null) {
		            	Utilitarios.logInfo(WebService.class.getName(), "Servidor Conexión establecida");
		                inputStream = httpEntity.getContent();
		                
		            } else {
		            	inputStream = null;
		            }
		        
		        }

				catch (ClientProtocolException e) {
					
					Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo ");
					
					e.printStackTrace();
				} catch (IOException e) {
					
					Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
					
					inputStream = null;
					e.printStackTrace();
				}
			return inputStream;
		}
	 
	 public static InputStream openHttpConnectionPostCompress(String url, String json, DefaultHttpClient httpClient) {

		 	Utilitarios.logError("ENTRA openHttpConnectionPostCompress", "openHttpConnectionPostCompress");
			InputStream inputStream = null;
			
			try
	        {
				Gson gson = new GsonBuilder().serializeNulls().create();
				
				byte[] uncompressed = json.toString().getBytes(HTTP.UTF_8);
				Utilitarios.createFileLog(new String(uncompressed), 1);
				
				Utilitarios.logInfo("", "Cadena original: " + uncompressed.length);
				byte[] compressed = Utilitarios.compress(uncompressed);
				
				Utilitarios.createFileLog(new String(compressed), 2);
				Log.e("", "Peso de la cadena comprimida: " + compressed.length);
				 
				String cadenaComprimida = Base64.encodeToString(compressed, Base64.DEFAULT);
				JsonEncriptado jsonEncriptado = new JsonEncriptado();
				jsonEncriptado.setCadena(cadenaComprimida);

//				Utilitarios.logError("", "JSON encriptado" + "*"+cadenaComprimida+"*");
//				Utilitarios.createFileLog1(gson.toJson(jsonEncriptado)+"");

				StringEntity entity = new StringEntity( gson.toJson(jsonEncriptado), HTTP.UTF_8);
				
				HttpParams httpParams = new BasicHttpParams();
				int timeoutConnection = 60000;
				HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
				httpClient.setParams(httpParams);
				
				
				HttpPost post = new HttpPost(url);
				post.setHeader("content-type", "application/json");
				
				post.setEntity(entity);

	        	HttpResponse httpResponse = httpClient.execute(post);
	        	

				 final int statusCode = httpResponse.getStatusLine().getStatusCode();
				 Utilitarios.logInfo(WebService.class.getName(), "ESTADO HTTP" + statusCode + " for URL "+ url);
				 
	            if (statusCode != HttpStatus.SC_OK) {
	            	Utilitarios.logError(WebService.class.getName(), "ESTADO HTTP" + "Error " + statusCode + " para URL "+ url);
	                return null;
	            }
	            
	            HttpEntity httpEntity = httpResponse.getEntity(); // la respuesta la trae en tipo httpEntity
	            if (httpEntity != null) {
	            	Utilitarios.logInfo(WebService.class.getName(), "Servidor Conexión establecida");
	                inputStream = httpEntity.getContent();
	                
	            } else {
	            	inputStream = null;
	            }
	        
	        } 

			catch (ClientProtocolException e) {
				Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
				e.printStackTrace();
			} catch (IOException e) {
				Utilitarios.logInfo(WebService.class.getName(), "Excedio el tiempo");
				inputStream = null;
				e.printStackTrace();
			}
			return inputStream;
		}
}
