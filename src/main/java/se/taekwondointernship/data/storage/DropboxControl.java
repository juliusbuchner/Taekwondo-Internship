package se.taekwondointernship.data.storage;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class DropboxControl {
    private static String ACCESS_TOKEN = "";
    private String baseFolder;

    public DropboxControl(String baseFolder){
        this.baseFolder = baseFolder;
    }

    public DbxClientV2 setUpClient(String ACCESS_TOKEN, String baseFolder) throws DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(baseFolder).build();
        return new DbxClientV2(config, ACCESS_TOKEN);
    }
    public String getData(String url) throws DbxException {
        DbxClientV2 client = setUpClient(ACCESS_TOKEN, baseFolder);
        return String.valueOf(client.files().download(url));
    }
    public void saveData(String url){
        try (InputStream in = new FileInputStream(url)){
            FileMetadata metadata = setUpClient(ACCESS_TOKEN, baseFolder).files().uploadBuilder("/"+url)
                    .uploadAndFinish(in);
        } catch (IOException | DbxException e) {
            throw new RuntimeException(e);
        }
    }
}
