
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket; 
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;



 public class server {  
	 //海报文本
	public static String listurl = "F:\\大三\\春季学期\\软件设计\\list.txt";
	//海报所在文件夹
	public static String imgurl = "F:\\大三\\春季学期\\软件设计\\post\\";
	
    public static void main(String[] args) throws IOException {  
        ServerSocket server_ = new ServerSocket(9999); 
        

        while (true) {  
            Socket socket = server_.accept();  
            invoke(socket);  
        }  
    }  

    private static void invoke(final Socket client) throws IOException {  
        new Thread(new Runnable() {  
            public void run() {  
            	BufferedReader in = null;  
            	DataOutputStream dos = null;
                FileInputStream fis = null;
                
             	String sql = null;
             	DBHelper db1 = null;
             	ResultSet ret = null;

                int length = 0;
                byte[] sendBytes = null;

                
                byte[] finishBytes = null;
                
                try {  
 
                    if (true) {  
                    	in = new BufferedReader(new InputStreamReader(client.getInputStream()));  
                        dos = new DataOutputStream(client.getOutputStream()); 
                        String msg = in.readLine(); 
                        System.out.println(msg);
                        if (msg.equals("bye")) {  
                            //break;  
                        }
                        else if(msg.equals("getList")){
                        	//PrintWriter out = new PrintWriter(client.getOutputStream());
                        	FileWriter fw = new FileWriter(listurl);
                        	msg = in.readLine();
                        	System.out.println(msg);
                        	if(msg.equals("0")){
                        		sql = "select * from postwall";
                        		db1 = new DBHelper(sql);//鍒涘缓DBHelper瀵硅薄
                        		try {
                        			ret = db1.pst.executeQuery();
                        			while (ret.next()) {
                        				String url = ret.getString(7);
                        				System.out.println(url);
                        				fw.write(url+"\r\n");
                        				
                        			}
                        		} catch (SQLException e) {
                        			e.printStackTrace();
                        		}
                        	}
                        	else{
                        		sql = "select * from postwall where tag = "+msg;
                        		db1 = new DBHelper(sql);//鍒涘缓DBHelper瀵硅薄
                        		try {
                        			ret = db1.pst.executeQuery();
                        			while (ret.next()) {
                        				String url = ret.getString(7);
                        				System.out.println(url);
                        				fw.write(url+"\r\n");
                        				
                        			}
                        		} catch (SQLException e) {
                        			e.printStackTrace();
                        		}
                        	}
                        	fw.close();
                        	dos = new DataOutputStream(client.getOutputStream());
                        	File file = new File(listurl);
                        	fis = new FileInputStream(file);
                        	sendBytes = new byte[1024];
                        	while ((length = fis.read(sendBytes, 0, sendBytes.length))  != -1) {
                        		dos.write(sendBytes, 0, length);
                        		dos.flush();
                        	}
                        	dos.close();
                        }
                        else if(msg.equals("getNum")){
                        	 sql = "select count(*) from postwall";
                     			db1 = new DBHelper(sql);//鍒涘缓DBHelper瀵硅薄
                     			try {
                     				ret = db1.pst.executeQuery();
                     				while (ret.next()) {
                     					String id = ret.getString(1);
                     					PrintWriter out = new PrintWriter(client.getOutputStream());
                     					System.out.println(id);
                     					out.println(id);
                     					out.close();
                     					break;
                     				
                     				}
                     			} catch (SQLException e) {
                     				e.printStackTrace();
                     		}
                        }
                        else if(msg.equals("getInfo")){
                        	msg = in.readLine();
                        	System.out.println(msg);
                        	sql = "select * from postwall where url = "+ msg;
                        	System.out.println(msg);
                        	db1 = new DBHelper(sql);//鍒涘缓DBHelper瀵硅薄
                        	try {
                        		ret = db1.pst.executeQuery();//鎵ц璇彞锛屽緱鍒扮粨鏋滈泦
                        		while (ret.next()) {
                        			PrintWriter out = new PrintWriter(client.getOutputStream());
                        			String title = ret.getString(3);
                        			String author = ret.getString(6);
                        			String time = ret.getString(4);
                        			String content = ret.getString(5);
                        			out.println(title);
                 					out.println(author);
                 					out.println(time);
                 					out.println(content);
                 					out.close();
                 					System.out.println(title + "\t" + author + "\t" + time + "\t" + content );
                 					break;

                        		}//鏄剧ず鏁版嵁
                        		ret.close();
                        		db1.close();//鍏抽棴杩炴帴
                        	} catch (SQLException e) {
                        		e.printStackTrace();
                        	}
                       }
                        else{
                        	//dos = new DataOutputStream(client.getOutputStream()); 
                        	msg = msg.substring(0,msg.length()-4);
                        	//System.out.println(msg);
                        
                        
                        	/*
                        	sql = "select * from postwall where id = " + msg;//SQL璇彞
                        	db1 = new DBHelper(sql);//鍒涘缓DBHelper瀵硅薄
                        	try {
                        		ret = db1.pst.executeQuery();//鎵ц璇彞锛屽緱鍒扮粨鏋滈泦
                        		while (ret.next()) {
                        			String id = ret.getString(1);
                        			String title = ret.getString(2);
                        			String content = ret.getString(3);
                        			String tag = ret.getString(4);
                        			System.out.println(id + "\t" + title + "\t" + content + "\t" + tag );
                        		}//鏄剧ず鏁版嵁
                        		ret.close();
                        		db1.close();//鍏抽棴杩炴帴
                        	} catch (SQLException e) {
                        		e.printStackTrace();
                        	}*/
                 		
                        
                        	dos = new DataOutputStream(client.getOutputStream());
                        	File file = new File(imgurl+msg+".jpg");
                        	fis = new FileInputStream(file);
                        	sendBytes = new byte[1024];
                        	while ((length = fis.read(sendBytes, 0, sendBytes.length))  != -1) {
                        		dos.write(sendBytes, 0, length);
                        		dos.flush();
                        	}
                        	dos.close();
                        
                        }  
                    }
                    System.out.println("end");
                } catch(IOException ex) {  
                    ex.printStackTrace();  
                } 
            }  
        }).start();  
    }  
}  
 
 

 