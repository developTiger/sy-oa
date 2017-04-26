package com.sunesoft.lemon.webapp.controller.files;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.criteria.FileCriteria;
import com.sunesoft.lemon.syms.fileSys.application.dtos.DownloadFileDto;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FilePathDto;
import com.sunesoft.lemon.syms.fileSys.domain.enums.DocType;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.ResouceFactory;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.SraFile;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/7/12.
 */
@Controller
public class FileController extends Layout {


    @Autowired
    UserSession us;
    @Autowired
    FileService fileService;

    @Autowired
    ResouceFactory resouceFactory;



    @Autowired
    DeptmentService deptmentService;

    @RequestMapping(value = "sra_f_file_manager")
    public ModelAndView fileUpload(Model model) {
        return view(layout, "files/fileManager", model);
    }


    @RequestMapping(value = "sra_f_file_query")
    public ModelAndView filequery(Model model) {

        //    List<FileInfoDto> files= fileService.GetFileInfo("userId:1");
        return view(layout, "files/fileQuery", model);
    }

    @RequestMapping(value = "/ajax_file_query")
    public void getFileByPathId(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        String path = request.getParameter("path");
        FileCriteria criteria = new FileCriteria();
        SraFile sraFile = new SraFile();
        Long searchDeptID = null;
        Long fid = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            fid = Long.parseLong(id);
        }
            List<FileInfoDto> files = new ArrayList<>();
            EmpSessionDto empSessionDto = us.getCurrentUser(request);
            String fileAuth = resouceFactory.getFileAuth(empSessionDto.getRoleIds());
            if (fileAuth != null) {
                if (fileAuth.equals("all")) {
                    sraFile.setCanCreateFloder(true);
                }
                if (fileAuth.equals("dept")) {
                    searchDeptID = empSessionDto.getDeptId();
                    if (fid == null) {
                        sraFile.setCanCreateFloder(false);
                    } else {
                        sraFile.setCanCreateFloder(true);
                    }
                  //criteria.setDeptId(empSessionDto.getDeptId());
                }
            } else {
                searchDeptID = empSessionDto.getDeptId();
                sraFile.setCanCreateFloder(false);
               // criteria.setDeptId(empSessionDto.getDeptId());
            }

            if (fid!=null) {
                criteria.setFilePathId(fid);
                files = fileService.GetFileInfo(criteria);
                sraFile.setParentId(fid);

            }

            List<FilePathDto> filePathDtos = fileService.GetFilePath(fid,searchDeptID);
            if (StringUtils.isNullOrWhiteSpace(path))
                path = "~";
            else
                path = URI.deURI(path);
            sraFile.setFiles(files);
            sraFile.setPath(path);
            //是否可以创建新文件夹（所有的都可以创建）


            sraFile.setFloders(filePathDtos);
            String json = JsonHelper.toJson(sraFile);
            AjaxResponse.write(response, json);
        }

        @RequestMapping(value = "/ajax_file_keyword_search")
        @ResponseBody
        public PagedResult<FileInfoDto> getFilebyKeyword (FileCriteria criteria, HttpServletRequest
        request){

            String keywords = request.getParameter("keywords");
            if (!StringUtils.isNullOrWhiteSpace(criteria.getFileName()))
                criteria.setFileName(URI.deURI(criteria.getFileName()));
            if (!StringUtils.isNullOrWhiteSpace(keywords)) {
                criteria.setKeywords(URI.deURI(keywords));
            }
            return fileService.GetFileInfoPaged(criteria);
        }

        @RequestMapping(value = "/ajax_get_floderAndfileById")
        public void getFloderAndfileById (HttpServletRequest request, HttpServletResponse response){
            String id = request.getParameter("id");
            FileCriteria criteria = new FileCriteria();
            Long fid = null;
            if (!StringUtils.isNullOrWhiteSpace(id)) {
                fid = Long.parseLong(id);
                criteria.setFilePathId(fid);
            }
            List<FilePathDto> filePathDtos = fileService.GetFilePath(fid);

            criteria.setUserId(1L);
            List<FileInfoDto> files = fileService.GetFileInfo(criteria);
            SraFile sraFile = new SraFile();
            sraFile.setFiles(files);
            sraFile.setFloders(filePathDtos);

            String json = JsonHelper.toJson(sraFile);
            AjaxResponse.write(response, json);
        }

        @RequestMapping(value = "_uploadFile")
        public ModelAndView upfile (Model model, HttpServletRequest request){
            String id = request.getParameter("id");
            String parentId = request.getParameter("parentId");
            if (!StringUtils.isNullOrWhiteSpace(id)) {
                FilePathDto dto = fileService.getFilePathById(Long.parseLong(id));
                model.addAttribute("bean", dto);
                model.addAttribute("$parentId", dto.getParentId());
            }
            if (!StringUtils.isNullOrWhiteSpace(parentId) && StringUtils.isNullOrWhiteSpace(id))
                model.addAttribute("$parentId", parentId);
            return view("files/_uploadFile", model);
        }

        @RequestMapping(value = "_addfloder")
        public ModelAndView addfloder (Model model, HttpServletRequest request){

            String id = request.getParameter("id");
            String parentId = request.getParameter("parentId");
            if (!StringUtils.isNullOrWhiteSpace(id)) {
                FilePathDto dto = fileService.getFilePathById(Long.parseLong(id));
                model.addAttribute("bean", dto);
                model.addAttribute("parentId", dto.getParentId());
            }
            List<DeptmentDto> list = deptmentService.getAllDept();
            model.addAttribute("depts", list);

            if (!StringUtils.isNullOrWhiteSpace(parentId) && StringUtils.isNullOrWhiteSpace(id))
                model.addAttribute("parentId", parentId);

            return view("files/_addfloder", model);
        }

        @RequestMapping(value = "ajax_add_update_floder")
        @ResponseBody
        public CommonResult addOrUpdateFloder (FilePathDto filePathDto, HttpServletRequest request, HttpServletResponse
        response){
            CommonResult result;
            if(filePathDto.getParentId()==null){ //根目录新增文件夹
                if(filePathDto.getBelongDept()==null){
                    filePathDto.setIsPublis(true);
                }

            }
             if (filePathDto.getId() != null && filePathDto.getId() > 0) {
                result = fileService.UpdateFilePath(filePathDto);
            } else {
                result = fileService.AddFilePath(filePathDto);
            }
            return result;
        }

        @RequestMapping(value = "/ajax_fileupload")
        public void uploadFile (HttpServletRequest request, HttpServletResponse response)throws IOException {
            String id = request.getParameter("id");
            CommonResult result;
            if (StringUtils.isNullOrWhiteSpace(id)) {
                result = new CommonResult(false, "sorry！根目录不允许上传文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return;
            }
            response.setContentType("text/plain; charset=UTF-8");
            //设置响应给前台内容的PrintWriter对象
            PrintWriter out = response.getWriter();

            EmpSessionDto emp = us.getCurrentUser(request);
            //上传文件的原名(即上传前的文件名字)
            String originalFilename = null;
            List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("myfiles");
            //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
            //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
            //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
            for (MultipartFile myfile : myfiles) {

                //if (myfile.isEmpty()) {
                if(myfile==null){
                    result = new CommonResult(false, "请选择要上传的文件！");
                    AjaxResponse.write(response, JsonHelper.toJson(result));
                    return;
                } else {
//                originalFilename = myfile.getOriginalFilename();
                    try {
                        String fileName = myfile.getOriginalFilename();
                        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        FileInfoDto fileInfoDto = new FileInfoDto();
                        fileInfoDto.setFilePathId(Long.parseLong(id));
                        fileInfoDto.setUserId(emp.getId());
                        fileInfoDto.setUserName(emp.getName());
//                    fileInfoDto.setUuidName(emp.getName());
//                        fileInfoDto.setDeptId(emp.getDeptId());
                       FilePathDto filePathDto= fileService.getFilePathById(Long.parseLong(id));
                        fileInfoDto.setDeptId(filePathDto.getBelongDept());
                        fileInfoDto.setDocType(DocType.DeptFile);
                        fileInfoDto.setFileName(fileName);
                        fileInfoDto.setExtensions(extension);
                        fileInfoDto.setInputStream(myfile.getInputStream());

                        fileService.upload(fileInfoDto);
                        //这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                        //此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
                        //    FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, UUID.randomUUID().toString()));
                    } catch (IOException e) {
                        System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                        e.printStackTrace();
                        out.print("1`文件上传失败，请重试！！");
                        out.flush();
                        result = new CommonResult(false, "文件上传失败，请重试！！");
                        AjaxResponse.write(response, JsonHelper.toJson(result));
                        return;
                    }
                }
            }

            out.flush();
            result = ResultFactory.commonSuccess();
            AjaxResponse.write(response, JsonHelper.toJson(result));
            return;
        }

    /**
     * 修改文件下载无后缀，且文件名乱码
     * edit by pxj
      * @param request
     * @param response
     * @return
     * @throws IOException
     */

        @RequestMapping(value = "/downloadFile")
        public String downloadFile (HttpServletRequest request, HttpServletResponse response)throws IOException {
            String id = request.getParameter("id");

            DownloadFileDto dto = fileService.getFileById(id);
            if(dto==null)return null;//xzl
            InputStream in = dto.getInputStream();
            //创建输出流
            OutputStream out = response.getOutputStream();
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(dto.getFileName(), "UTF-8"));
           // response.setHeader("content-disposition", "attachment;filename=" + new String(dto.getFileName().getBytes("gb2312"),"iso8859-1"));
            //创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            //循环将输入流中的内容读取到缓冲区当中
            while ((len = in.read(buffer)) > 0) {
                //输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
            //关闭文件输入流
            in.close();
            //关闭输出流
            out.close();
            return null;
        }


        @RequestMapping(value = "/ajax_deleteFile")
        @ResponseBody
        public CommonResult deleteFile (HttpServletRequest request, HttpServletResponse response)throws IOException {
            String id = request.getParameter("id");
            CommonResult result = fileService.delete(id);
            return result;
        }

        @RequestMapping(value = "/ajax_deleteFloder")
        @ResponseBody
        public CommonResult deleteFloder (HttpServletRequest request, HttpServletResponse response)throws IOException {
            String id = request.getParameter("id");
            CommonResult result = fileService.deleteFilePath(Long.parseLong(id));
            return result;
        }
    }
