package com.boiler_plate.mobile.managers;

import com.boiler_plate.mobile.Constants;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by juanpablogarcia on 4/4/16.
 */
public class DocumentDownloader {
    private Set<String> downloadedDocuments;
    public boolean permissionsWriteExternalStorage;

    @Inject
    public DocumentDownloader() {
        this.downloadedDocuments = new HashSet<>();
        this.permissionsWriteExternalStorage = true;
    }

    public Boolean needsWritingPermission() {
        return permissionsWriteExternalStorage;
    }

    public Observable<Boolean> downloadDocument(final String documentURL) {
        if (!downloadedDocuments.contains(documentURL)) {
            return Observable.create(new Observable.OnSubscribe<Boolean>() {
                @Override
                public void call(Subscriber<? super Boolean> subscriber) {
                    try {
                        downloadFile(documentURL);
                        downloadDocument(documentURL);
                        subscriber.onNext(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriber.onNext(false);
                    }
                }
            });
        } else {
            return Observable.just(true);
        }
    }

    public void downloadFile(String fileURL) throws Exception {
        String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
        int count;
        URL url = new URL(fileURL);
        URLConnection connection = url.openConnection();
        connection.connect();
        InputStream input = new BufferedInputStream(url.openStream());
        OutputStream output = new FileOutputStream(Constants.DOWNLOAD_FOLDER + fileName);
        byte data[] = new byte[1024];
        while ((count = input.read(data)) != -1) {
            output.write(data, 0, count);
        }
        output.flush();
        output.close();
        input.close();
    }
}

