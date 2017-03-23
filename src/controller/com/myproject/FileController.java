// FileController.java

package controller.com.myproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import model.com.myproject.FileDAO;
import model.com.myproject.FileDTO;

@Controller
public class FileController implements ApplicationContextAware {

	private WebApplicationContext context = null;

	// get방식으로 얻어어옴
	@RequestMapping(value = "/fileDown.do", method = RequestMethod.GET)
	public ModelAndView fileDown(@RequestParam("fileName") String fileName,
			@RequestParam("folderName") String folderName) {
		String fullPath;
		if (folderName != "") { // if folderName is not null
			System.out.println("if:folderName=" + folderName);
			fullPath = "C:/myfiles/" + folderName + "/" + fileName;
			System.out.println(fullPath);
		} else { // if folderName is null
			System.out.println("else:folderName=" + folderName);
			fullPath = "C:/myfiles/" + fileName;
			System.out.println(fullPath);
		}
		// System.out.println(folderName);
		// System.out.println(fullPath);
		File downloadFile = new File(fullPath);
		// ModelAndView(String viewName,String modelName,Object modelObject)
		return new ModelAndView("download", "downloadFile", downloadFile);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = (WebApplicationContext) applicationContext;
	}

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ModelAndView fileList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fileList");
		ArrayList<FileDTO> list = FileDAO.getInstance().firstPageList();
		mv.addObject("list", list); //the object to add to the model
		return mv;
	}

	// get방식
	@RequestMapping(value = "/file.do", method = RequestMethod.GET)
	public ModelAndView fileForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fileForm");
		return mv;
	}

	// post방식
	@RequestMapping(value = "/file.do", method = RequestMethod.POST)
	public String fileSubmit(FileDTO dto) {
		MultipartFile uploadfile = dto.getUploadfile();// ???how to get
														// parameter from
														// fileForm.jsp
		if (uploadfile != null && dto.getFolderName() == null) {
			String fileName = uploadfile.getOriginalFilename();// 파일 이름 얻어옴
			String fileExtension = fileName.substring(fileName.lastIndexOf("."));
			long fileSize = uploadfile.getSize();// 파일 사이즈 얻어옴

			dto.setFileName(fileName);
			dto.setFileExtension(fileExtension);
			dto.setFileSize(fileSize);
			// System.out.println("/file.do="+dto);
			// System.out.println(dto);
			try {
				// 1. FileOutputStream 사용
				// byte[] fileData = file.getBytes();
				// FileOutputStream output = new FileOutputStream("C:/images/" +
				// fileName);
				// output.write(fileData);
				// 2. File 사용
				File file = new File("C:/myfiles/" + fileName);
				if (file.exists() == false) {
					file.mkdirs();
				}
				uploadfile.transferTo(file);// 파일 전송
			} catch (IOException e) {
				e.printStackTrace();
			} // try - catch
			FileDAO.getInstance().insertFile(dto);// dao로 db에 접근하여 파일정보를 올린다.
			return "redirect:list.do";

		} else if (uploadfile != null && dto.getFolderName() != null) {
			String fileName = uploadfile.getOriginalFilename();// 파일 이름 얻어옴
			String fileExtension = fileName.substring(fileName.lastIndexOf("."));
			long fileSize = uploadfile.getSize();// 파일 사이즈 얻어옴

			dto.setFileName(fileName);
			dto.setFileExtension(fileExtension);
			dto.setFileSize(fileSize);
			System.out.println("/file.do=" + dto);
			try {
				// 1. FileOutputStream 사용
				// byte[] fileData = file.getBytes();
				// FileOutputStream output = new FileOutputStream("C:/images/" +
				// fileName);
				// output.write(fileData);

				// 2. File 사용
				File file = new File("C:/myfiles/" + dto.getFolderName() + "/" + fileName);
				if (file.exists() == false) {
					file.mkdirs();
				}
				uploadfile.transferTo(file);// 파일 전송
			} catch (IOException e) {
				e.printStackTrace();
			} // try - catch
			FileDAO.getInstance().incrementSeq(dto.getRef(), dto.getSeq());
			FileDAO.getInstance().insertFileToFolder(dto);// dao로 db에 접근하여 파일정보를
															// 올린다.
			return "redirect:folderContentView.do?idx=" + dto.getIdx() + "&ref=" + dto.getRef() + "&lev="
					+ dto.getLev();
		} else {
			return "redirect:list.do";
		}
	}

	// post방식
	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	// @ResponseBody
	public ModelAndView boardFileDelete(@RequestParam("fileName") String fileName, @RequestParam("idx") int idx) {
		ModelAndView mv = new ModelAndView("redirect:/list.do");
		String fullPath = "C:/myfiles/" + fileName;
		File file = new File(fullPath);
		if (file.exists() == true) {
			file.delete();
		}
		FileDAO.getInstance().deleteFile(idx);
		return mv;
	}

	// get방식,파라미터 x
	@RequestMapping(value = "/iconView.do", method = RequestMethod.GET)
	public ModelAndView iconView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("iconView");

		ArrayList<FileDTO> list = FileDAO.getInstance().firstPageList();// 읽어온
																		// 파일정보를
																		// ArrayList에
																		// 저장
		mv.addObject("list", list); // modelObject 설정

		return mv;
	}

	// folderContentView.jsp로 이동 , idx,ref,lev 파라미터 받음
	@RequestMapping(value = "/iconFolderContentView.do", method = RequestMethod.GET)
	public ModelAndView iconFolderContentView(@RequestParam("idx") int idx, @RequestParam("ref") int ref,
			@RequestParam("lev") int lev) {
		// System.out.println("ref="+ref+" idx="+idx);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("iconView");
		ArrayList<FileDTO> list = FileDAO.getInstance().listFolder(ref, lev);// ref로
																				// 읽어온
																				// 파일정보를
																				// ArrayList에
																				// 저장
		FileDTO dto = FileDAO.getInstance().selectByIdx(idx);// 폴더의 idx로 db에 접근해
																// 한건의 dto객체를
																// 만든다.
		mv.addObject("list", list); // modelObject 설정
		mv.addObject("dto", dto); // modelObject 설정
		// System.out.println(dto);
		return mv;
	}

	// get방식
	@RequestMapping(value = "/calendarView2.do", method = RequestMethod.GET)
	public ModelAndView calendarView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("calendarView2");
		ArrayList<FileDTO> list = FileDAO.getInstance().allPageList();// 읽어온
																		// 모든페이지
																		// 파일정보를
																		// ArrayList에
																		// 저장
		mv.addObject("list", list); // modelObject 설정
		return mv;
	}

	// 파일리스트를 불러서 createFolderView.jsp로 이동
	@RequestMapping(value = "/createFolder.do", method = RequestMethod.GET)
	public ModelAndView createFolderForm(@RequestParam("job") String job, FileDTO dto) {
		ModelAndView mv = new ModelAndView();
		if (job.equals("firstPageList")) {// 최초 폴더를 만들경우
			mv.setViewName("createFolderView");
			ArrayList<FileDTO> list = FileDAO.getInstance().firstPageList();// 읽어온
																			// 파일정보를
																			// ArrayList에
																			// 저장
			mv.addObject("list", list); // modelObject 설정
		} else if (job.equals("listFolder")) {// 폴더안에 폴더를 만들경우
			System.out.println("listFolder " + dto);
			mv.setViewName("createFolderView2");
			ArrayList<FileDTO> list = FileDAO.getInstance().listFolder(dto.getRef(), dto.getLev());// 읽어온
																									// 파일정보를
																									// ArrayList에
																									// 저장
			mv.addObject("dto", dto); // modelObject 설정
			mv.addObject("list", list); // modelObject 설정
			FileDAO.getInstance().incrementSeq(dto.getRef(), dto.getSeq());// 다른글의
																			// seq증가.
		}
		return mv;
	}

	// createFolderView.jsp에서 제목을 가져와 새폴더를 만든다.
	@RequestMapping(value = "/createFolder.do", method = RequestMethod.POST)
	public String createFolder(FileDTO dto) {
		dto.setFileExtension(".folder");
		FileDAO.getInstance().createFolder(dto);
		return "redirect:list.do";
	}

	// createFolderView.jsp에서 제목을 가져와 새폴더를 만든다.
	@RequestMapping(value = "/createFolderInFolder.do", method = RequestMethod.POST)
	public String createFolderInFolder(FileDTO dto, @RequestParam("NewfolderName") String NewfolderName) {
		dto.setFileExtension(".folder");
		FileDAO.getInstance().createFolderInFolder(dto, NewfolderName);
		return "redirect:folderContentView.do?idx=" + dto.getIdx() + "&ref=" + dto.getRef() + "&lev=" + dto.getLev();
	}

	// folderContentView.jsp로 이동
	@RequestMapping(value = "/folderContentView.do", method = RequestMethod.GET)
	public ModelAndView folderContentView(@RequestParam("idx") int idx, @RequestParam("ref") int ref,
			@RequestParam("lev") int lev) {
		System.out.println("ref=" + ref + " idx=" + idx + " lev=" + lev);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("folderContentView");
		ArrayList<FileDTO> list = FileDAO.getInstance().listFolder(ref, lev);// ref로
																				// 읽어온
																				// 파일정보를
																				// ArrayList에
																				// 저장
																				// 폴더레벨+1인
																				// 목록얻어옴
		FileDTO dto = FileDAO.getInstance().selectByIdx(idx);// 폴더의 idx로 db에 접근해
																// 한건의 dto객체를
																// 만든다.
		mv.addObject("list", list); // modelObject 설정
		mv.addObject("dto", dto); // modelObject 설정
		// System.out.println(dto);
		return mv;
	}

	// folderContentView.jsp로 이동
	@RequestMapping(value = "/search.do", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam("keyword") String keyword,
			@RequestParam("searchFilter") String searchFilter) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fileList");
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		switch (searchFilter) {
		case "fileName":
			list = FileDAO.getInstance().searchByFilename(keyword);
			break;
		case "folderName":
			list = FileDAO.getInstance().searchByFoldername(keyword);
			break;
		case "fileNameAndFolderName":
			list = FileDAO.getInstance().searchByFilenameAndFoldername(keyword);
			break;
		default:
			list = FileDAO.getInstance().searchByFilename(keyword);// ref로 읽어온
																	// 파일정보를
																	// ArrayList에
																	// 저장
		}
		mv.addObject("list", list); // modelObject 설정
		return mv;
	}

	int toggle = 1;

	// folderContentView.jsp로 이동
	@RequestMapping(value = "/sort.do", method = RequestMethod.GET)
	public ModelAndView sort(@RequestParam("sortType") String sortType, @RequestParam("sortWay") String sortWay) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fileList");
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();

		list = FileDAO.getInstance().sort(sortType, sortWay); // 읽어온 파일정보를
																// ArrayList에 저장

		mv.addObject("idxSortWay", "");
		mv.addObject("dateSortWay", "");
		mv.addObject("filenameSortWay", "");

		if (toggle == 1 && sortType.equals("idx")) {
			mv.addObject("idxSortWay", "asc");
			toggle = 0;
		} else if (toggle == 0 && sortType.equals("idx")) {
			mv.addObject("idxSortWay", "desc");
			toggle = 1;
		} else if (toggle == 1 && sortType.equals("wdate")) {
			mv.addObject("dateSortWay", "asc");
			toggle = 0;
		} else if (toggle == 0 && sortType.equals("wdate")) {
			mv.addObject("dateSortWay", "desc");
			toggle = 1;
		} else if (toggle == 1 && sortType.equals("filename")) {
			mv.addObject("fileNameSortWay", "asc");
			toggle = 0;
		} else if (toggle == 0 && sortType.equals("filename")) {
			mv.addObject("fileNameSortWay", "desc");
			toggle = 1;
		} else if (toggle == 1 && sortType.equals("fileSize")) {
			mv.addObject("fileSizeSortWay", "asc");
			toggle = 0;
		} else if (toggle == 0 && sortType.equals("fileSize")) {
			mv.addObject("fileSizeSortWay", "desc");
			toggle = 1;
		}
		mv.addObject("list", list); // modelObject 설정

		return mv;
	}
}
