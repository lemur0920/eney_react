package eney.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Component
public class SFTPUtil{

	private static final Logger logger = LoggerFactory.getLogger(Service.class);
    private Session session = null;
    private Channel channel = null;

    private JSch jsch;
    private ChannelSftp sftpChannel;
    
    public void connect(String user, String host, int port, String password) throws JSchException {
        System.out.println("connecting..."+host);
        // 1. JSch 객체를 생성한다.
        jsch = new JSch();
        // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
        session = jsch.getSession(user, host,port);
        // 4. 세션과 관련된 정보를 설정한다.
        session.setConfig("StrictHostKeyChecking", "no");
        // 4. 패스워드를 설정한다.
        session.setPassword(password);
        // 5. 접속한다.
        session.connect();

        // 6. sftp 채널을 연다.
        channel = session.openChannel("sftp");
        // 7. 채널에 연결한다.
        channel.connect();
        // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
        sftpChannel = (ChannelSftp) channel;
    }
    
    public void disconnect() {
        if(session.isConnected()){
            System.out.println("disconnecting...");
            sftpChannel.disconnect();
            channel.disconnect();
            session.disconnect();
        }
    }
    
    public void upload(String fileName,Path fileUploadPath, String remoteDir,String user, String host, int port, String password) throws Exception {
        FileInputStream fis = null;
        // 앞서 만든 접속 메서드를 사용해 접속한다.
        connect(user,host,port,password);
        try {
            // Change to output directory
            sftpChannel.cd(remoteDir);
            // Upload file
            File file = new File(fileUploadPath.toAbsolutePath().toString());
            // 입력 파일을 가져온다.
            fis = new FileInputStream(file);
            // 파일을 업로드한다.
            sftpChannel.put(fis, file.getName());
            fis.close();
            System.out.println("File uploaded successfully - "+ file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }
    
    public void download(String fileName, String localDir, HttpServletRequest request, String user, String host, int port, String password) throws Exception{
        byte[] buffer = new byte[1024];
        BufferedInputStream bis;
        connect(user,host,port,password);
        localDir = request.getClass().getResource("/static/resources/rcv").getPath();
        try {
            // Change to output directory
            String cdDir = fileName.substring(0, fileName.lastIndexOf("/") + 1);
            sftpChannel.cd(cdDir);

            File file = new File(fileName);
            bis = new BufferedInputStream(sftpChannel.get(file.getName()));

            File newFile = new File(localDir + "/" + file.getName());

            // Download file
            OutputStream os = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int readCount;
            while ((readCount = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, readCount);
            }
            bis.close();
            bos.close();
            System.out.println("File downloaded successfully - "+ file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }    
}

