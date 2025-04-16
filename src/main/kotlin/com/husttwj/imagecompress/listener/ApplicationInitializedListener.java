package com.husttwj.imagecompress.listener;


import com.husttwj.imagecompress.action.CompressAction;
import com.husttwj.imagecompress.util.FileUtils;

import com.intellij.openapi.vfs.*;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ApplicationInitializedListener implements com.intellij.ide.ApplicationInitializedListener {

    @Override
    public void componentsInitialized() {
        FileUtils.init();

        registerVirtualFileListener();

    }

    public static LinkedList<VirtualFile> sAddImageFiles = new LinkedList<>();

    private void registerVirtualFileListener() {
        VirtualFileManager.getInstance().addVirtualFileListener(new VirtualFileAdapter() {
            @Override
            public void fileCreated(@NotNull VirtualFileEvent event) {
                if (!(event instanceof VirtualFileCopyEvent)) {
                    return;
                }
                super.fileCreated(event);
                final VirtualFile file = event.getFile();
                if (!CompressAction.sPredicate.test(file)) {
                    return;
                }
                sAddImageFiles.add(file);
            }
        });
    }



}
