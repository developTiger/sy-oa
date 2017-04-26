package com.sunesoft.lemon.syms.fileSys.application;

import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.fileSys.domain.enums.DocType;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;
import java.util.*;

/**
 * Created by MJ006 on 2016/7/11.
 */
@Component
public class LuceneIndexManger {

    private LuceneIndexManger indexManager;
    private String content = "";

    private String INDEX_DIR = "D:\\luceneIndex";
    private String DATA_DIR = "D:\\luceneData";
    private Analyzer analyzer = null;
    private Directory directory = null;
    private IndexWriter indexWriter = null;

//    /**
//     * 创建索引管理器
//     * @return 返回索引管理器对象
//     */
//    public LuceneIndexManger getManager(){
//        if(indexManager == null){
//            this.indexManager = new LuceneIndexManger();
//        }
//        return indexManager;
//    }

    /**
     * 创建当前文件目录的索引
     *
     * @param fileInfos 当前上传文件
     * @return 是否成功
     */
    public UUID createIndex(FileInfoDto fileInfos) {
        Date date1 = new Date();

        UUID id = UUID.randomUUID();
        File file = new File("D:\\luceneData\\", fileInfos.getUuidName());
        try {
            FileUtils.copyInputStreamToFile(fileInfos.getInputStream(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\luceneData\\" + fileInfos.getUuidName());

//        List<File> fileList = getFileList(path);
//        List<File> fileList = getFileList(fileInfos.getFilePath().getPathName());
//        for (File file : fileList) {
            content = "";
            //获取文件后缀
            String type = fileInfos.getExtensions(); //file.getName().substring(file.getName().lastIndexOf(".")+1);
            if ("txt".equalsIgnoreCase(type)) {

                content += txt2String(fileInputStream);

            } else if ("doc".equalsIgnoreCase(type)) {

                content += doc2String(fileInputStream);

            } else if ("xls".equalsIgnoreCase(type)) {

                content += xls2String(fileInputStream);
            } else if ("docx".equalsIgnoreCase(type)) {
                content += docx2String(fileInputStream);
            } else if ("xlsx".equalsIgnoreCase(type)) {
                content += xls2String(fileInputStream);
            }

            System.out.println("name :" + fileInfos.getFileName());
            System.out.println("path :" + fileInfos.getFilePathId());
//            System.out.println("content :"+content);
            System.out.println();


            try {
                analyzer = new IKAnalyzer5x();// new StandardAnalyzer(Version.LUCENE_CURRENT);
                directory = FSDirectory.open(new File(INDEX_DIR).toPath());
//            TokenStream stream = analyzer.tokenStream("xx", new StringReader(content));
//            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
//            stream.reset();
//            while (stream.incrementToken()) {
//                System.out.print("[" + cta + "] ");
//            }
//            stream.close();

                File indexFile = new File(INDEX_DIR);
                if (!indexFile.exists()) {
                    indexFile.mkdirs();
                }
                IndexWriterConfig config = new IndexWriterConfig(analyzer);
                indexWriter = new IndexWriter(directory, config);

                Document document = new Document();
                document.add(new TextField("id", id.toString(), Field.Store.YES));
                if (fileInfos.getResourceId() != null)
                    document.add(new TextField("resourceId", fileInfos.getResourceId().toString(), Field.Store.YES));
                if (fileInfos.getUserDefineType() != null)
                    document.add(new TextField("userDefineType", fileInfos.getUserDefineType(), Field.Store.YES));
                document.add(new TextField("uuidName", fileInfos.getUuidName().toString(), Field.Store.YES));
                document.add(new TextField("filename", fileInfos.getFileName() , Field.Store.YES));
                document.add(new TextField("content", content, Field.Store.YES));

                if (fileInfos.getFilePathId() != null)
                    document.add(new TextField("path", fileInfos.getFilePathId().toString(), Field.Store.YES));
                if (fileInfos.getDocType() != null)
                    document.add(new TextField("docType", fileInfos.getDocType().toString(), Field.Store.YES));
                if (fileInfos.getIsPublic() != null)
                    document.add(new TextField("isPublic", fileInfos.getIsPublic().toString(), Field.Store.YES));
                if (fileInfos.getDeptId() != null)
                    document.add(new TextField("deptId", fileInfos.getDeptId().toString(), Field.Store.YES));
                if (fileInfos.getUserId() != null)
                    document.add(new TextField("userId", fileInfos.getUserId().toString(), Field.Store.YES));
                if (!StringUtils.isNullOrWhiteSpace(fileInfos.getUserName()))
                    document.add(new TextField("userName", fileInfos.getUserName(), Field.Store.YES));

                document.add(new TextField("createDate", DateHelper.formatDate(new Date()), Field.Store.YES));
                document.add(new TextField("extensions", fileInfos.getExtensions().toString(), Field.Store.YES));
                indexWriter.addDocument(document);
                indexWriter.commit();

                closeWriter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        content = "";
//        }
        Date date2 = new Date();
        System.out.println("创建索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
        return id;
    }

    /**
     * 读取txt文件的内容
     *
     * @param fileStream 想要读取的文件对象流
     * @return 返回文件内容
     */
    public String txt2String(InputStream fileStream) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result = result + "\n" + s;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取doc文件内容
     *
     * @param fis 想要读取的文件对象
     * @return 返回文件内容
     */
    public String doc2String(InputStream fis) {
        String result = "";
        try {
//
//            FileInputStream fis = new FileInputStream(file);
            HWPFDocument doc = new HWPFDocument(fis);
            Range rang = doc.getRange();
            result += rang.text();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取doc文件内容
     *
     * @param fis 想要读取的文件对象
     * @return 返回文件内容
     */
    public String docx2String(InputStream fis) {
        String result = "";
        try {

            XWPFDocument doc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String text = extractor.getText();
            result += text;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取xls文件内容
     *
     * @param fis 想要读取的文件对象
     * @return 返回文件内容
     */
    public String xls2String(InputStream fis) {
        String result = "";
        try {
//            FileInputStream fis = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            jxl.Workbook rwb = Workbook.getWorkbook(fis);
            Sheet[] sheet = rwb.getSheets();
            for (int i = 0; i < sheet.length; i++) {
                Sheet rs = rwb.getSheet(i);
                for (int j = 0; j < rs.getRows(); j++) {
                    Cell[] cells = rs.getRow(j);
                    for (int k = 0; k < cells.length; k++)
                        sb.append(cells[k].getContents());
                }
            }
            result += sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查找索引，返回符合条件的文件
     *
     * @param text 查找的字符串
     * @return 符合条件的文件List
     */
    public PagedResult<FileInfoDto> searchIndex(String text, Integer pageSize, Integer pageNumber) {
        Date date1 = new Date();
        Integer totalCount = 0;
        List<FileInfoDto> list = new ArrayList<>();
        try {
            directory = FSDirectory.open(new File(INDEX_DIR).toPath());
            analyzer = new IKAnalyzer5x();
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);

            QueryParser parser = new QueryParser("", analyzer);

            Query query = parser.parse(text);

            totalCount = isearcher.count(query);
            Integer start = (pageNumber - 1) * pageSize;

            TopFieldCollector c = TopFieldCollector.create(Sort.INDEXORDER, start + pageSize, false, false, false);
            isearcher.search(query, c);
            ScoreDoc[] hits = c.topDocs(start, pageSize).scoreDocs;
            // ScoreDoc[] hits = isearcher.search(query, 1000, Sort.INDEXORDER).scoreDocs;

            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                System.out.println("____________________________");
                System.out.println(hitDoc.get("filename"));
                System.out.println(hitDoc.get("content"));
                System.out.println(hitDoc.get("path"));
                System.out.println("____________________________");
                FileInfoDto fileInfoDto = new FileInfoDto();

                fileInfoDto.setId(UUID.fromString(hitDoc.get("id")));
                if (hitDoc.get("docType") != null)
                    fileInfoDto.setDocType(Enum.valueOf(DocType.class, hitDoc.get("docType")));
                if (hitDoc.get("resourceId") != null)
                    fileInfoDto.setResourceId(hitDoc.get("resourceId"));
                if (hitDoc.get("userDefineType") != null)
                    fileInfoDto.setUserDefineType(hitDoc.get("userDefineType"));
                fileInfoDto.setUuidName(hitDoc.get("uuidName"));
                fileInfoDto.setFileName(hitDoc.get("filename"));
//                fileInfoDto.setFilePathId(hitDoc.get("content"));
//                fileInfoDto.setFilePathId(Long.parseLong(hitDoc.get("path")));
                if (hitDoc.get("isPublic") != null)
                    fileInfoDto.setIsPublic(Boolean.getBoolean(hitDoc.get("isPublic")));
                if (hitDoc.get("deptId") != null)
                    fileInfoDto.setDeptId(Long.parseLong(hitDoc.get("deptId")));
                if (hitDoc.get("userId") != null)
                    fileInfoDto.setUserId(Long.parseLong(hitDoc.get("userId")));
                if (hitDoc.get("userName") != null)
                    fileInfoDto.setUserName(hitDoc.get("userName"));
                fileInfoDto.setCreateDate(hitDoc.get("createDate"));
                fileInfoDto.setExtensions(hitDoc.get("extensions"));
                list.add(fileInfoDto);
            }
            ireader.close();
            directory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date2 = new Date();
        System.out.println("查看索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");

//        list.sort(new Comparator<FileInfoDto>() {
//            @Override
//            public int compare(FileInfoDto o1, FileInfoDto o2) {
//                return o2.getCreateDate().compareTo(o1.getCreateDate());
//            }
//        });
        return new PagedResult<FileInfoDto>(list, pageNumber, pageSize, totalCount);
    }

    /**
     * 查找索引，返回符合条件的文件
     *
     * @param text 查找的字符串
     * @return 符合条件的文件List
     */
    public List<FileInfoDto> searchIndex(String text) {
        Date date1 = new Date();
        List<FileInfoDto> list = new ArrayList<>();
        try {
            directory = FSDirectory.open(new File(INDEX_DIR).toPath());
            analyzer = new IKAnalyzer5x();
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);

            QueryParser parser = new QueryParser("content", analyzer);

            Query query = parser.parse(text);

            ScoreDoc[] hits = isearcher.search(query, 1000, Sort.INDEXORDER).scoreDocs;

            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                System.out.println("____________________________");
                System.out.println(hitDoc.get("filename"));
                System.out.println(hitDoc.get("content"));
                System.out.println(hitDoc.get("path"));
                System.out.println("____________________________");
                FileInfoDto fileInfoDto = new FileInfoDto();

                fileInfoDto.setId(UUID.fromString(hitDoc.get("id")));
                if (hitDoc.get("docType") != null)
                    fileInfoDto.setDocType(Enum.valueOf(DocType.class, hitDoc.get("docType")));
                if (hitDoc.get("resourceId") != null)
                    fileInfoDto.setResourceId(hitDoc.get("resourceId"));
                if (hitDoc.get("userDefineType") != null)
                    fileInfoDto.setUserDefineType(hitDoc.get("userDefineType"));
                fileInfoDto.setUuidName(hitDoc.get("uuidName"));
                fileInfoDto.setFileName(hitDoc.get("filename"));
//                fileInfoDto.setFilePathId(hitDoc.get("content"));
//                fileInfoDto.setFilePathId(Long.parseLong(hitDoc.get("path")));
                if (hitDoc.get("isPublic") != null)
                    fileInfoDto.setIsPublic(Boolean.getBoolean(hitDoc.get("isPublic")));
                if (hitDoc.get("deptId") != null)
                    fileInfoDto.setDeptId(Long.parseLong(hitDoc.get("deptId")));
                if (hitDoc.get("userId") != null)
                    fileInfoDto.setUserId(Long.parseLong(hitDoc.get("userId")));
                if (hitDoc.get("userName") != null)
                    fileInfoDto.setUserName(hitDoc.get("userName"));
                fileInfoDto.setCreateDate(hitDoc.get("createDate"));
                fileInfoDto.setExtensions(hitDoc.get("extensions"));
                list.add(fileInfoDto);
            }
            ireader.close();
            directory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date2 = new Date();
        System.out.println("查看索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
        return list;
    }

    /**
     * 查找索引，返回符合条件的文件
     *
     * @param id 查找的id
     * @return 符合条件的文件List
     */
    public FileInfoDto searchIndexById(String id) {
        Date date1 = new Date();
        FileInfoDto fileInfoDto = new FileInfoDto();
        try {



            directory = FSDirectory.open(new File(INDEX_DIR).toPath());
            analyzer = new IKAnalyzer5x();
            DirectoryReader ireader = DirectoryReader.open(directory);
            IndexSearcher isearcher = new IndexSearcher(ireader);

            Query query =new TermQuery(new Term("id",id));
//            QueryParser parser = new QueryParser("content", analyzer);
//            Query query = parser.parse("(id:" + id+")");

            ScoreDoc[] hits = isearcher.search(query, 1000, Sort.INDEXORDER).scoreDocs;

            if (hits.length > 0) {
                Document hitDoc = isearcher.doc(hits[0].doc);
                System.out.println("____________________________");
                System.out.println(hitDoc.get("filename"));
                System.out.println(hitDoc.get("content"));
                System.out.println(hitDoc.get("path"));
                System.out.println("____________________________");
                fileInfoDto.setId(UUID.fromString(hitDoc.get("id")));
                if (hitDoc.get("docType") != null)
                    fileInfoDto.setDocType(Enum.valueOf(DocType.class, hitDoc.get("docType")));
                if (hitDoc.get("resourceId") != null)
                    fileInfoDto.setResourceId(hitDoc.get("resourceId"));
                if (hitDoc.get("userDefineType") != null)
                    fileInfoDto.setUserDefineType(hitDoc.get("userDefineType"));
                fileInfoDto.setUuidName(hitDoc.get("uuidName"));
                fileInfoDto.setFileName(hitDoc.get("filename"));
//                fileInfoDto.setFilePathId(hitDoc.get("content"));
//                fileInfoDto.setFilePathId(Long.parseLong(hitDoc.get("path")));
                if (hitDoc.get("isPublic") != null)
                    fileInfoDto.setIsPublic(Boolean.getBoolean(hitDoc.get("isPublic")));
                if (hitDoc.get("deptId") != null)
                    fileInfoDto.setDeptId(Long.parseLong(hitDoc.get("deptId")));
                if (hitDoc.get("userId") != null)
                    fileInfoDto.setUserId(Long.parseLong(hitDoc.get("userId")));
                fileInfoDto.setExtensions(hitDoc.get("extensions"));

            }
            ireader.close();
            directory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileInfoDto;
    }

    /**
     * 过滤目录下的文件
     *
     * @param dirPath 想要获取文件的目录
     * @return 返回文件list
     */
    public List<File> getFileList(String dirPath) {
        File[] files = new File(dirPath).listFiles();
        List<File> fileList = new ArrayList<File>();
        for (File file : files) {
            if (isTxtFile(file.getName())) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 判断是否为目标文件，目前支持txt xls doc格式
     *
     * @param fileName 文件名称
     * @return 如果是文件类型满足过滤条件，返回true；否则返回false
     */
    public boolean isTxtFile(String fileName) {
        if (fileName.lastIndexOf(".txt") > 0) {
            return true;
        } else if (fileName.lastIndexOf(".xls") > 0) {
            return true;
        } else if (fileName.lastIndexOf(".doc") > 0) {
            return true;
        }
        return false;
    }

    public void closeWriter() throws Exception {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }

    /**
     * 删除文件目录下的所有文件
     * 要删除的文件目录
     *
     * @return 如果成功，返回true.
     */
    public boolean deleteDir(String fileName) {
//        if(file.isDirectory()){
//            File[] files = file.listFiles();
//            for(int i=0; i<files.length; i++){
//                deleteDir(files[i]);
//            }
//        }
//        file.delete();

        File file = new File(INDEX_DIR + fileName);
        file.delete();

        return true;
    }
//    public  void main(String[] args){
//        File fileIndex = new File(INDEX_DIR);
//        if(deleteDir(fileIndex)){
//            fileIndex.mkdir();
//        }else{
//            fileIndex.mkdir();
//        }
//
//        createIndex(DATA_DIR);
//        searchIndex("man");
//    }

    /**
     * 2      * 删除索引
     * 3      *
     * 4      * @param str 删除的关键字
     * 5      * @throws Exception
     * 6
     */
    public void delete(String id) throws Exception {
        Date date1 = new Date();
//             analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
        analyzer = new IKAnalyzer5x();
//             directory = FSDirectory.open(new File(INDEX_DIR));
        directory = FSDirectory.open(new File(INDEX_DIR).toPath());
//             IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(directory, config);
//        QueryParser parser = new QueryParser("content", analyzer);
//        Query query = parser.parse("id:" + id);
        Query query =new TermQuery(new Term("id",id));
        indexWriter.deleteDocuments(query);

        indexWriter.close();

    }
}
