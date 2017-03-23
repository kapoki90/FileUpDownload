// FileDTO.java

package model.com.myproject;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO {//db�� �� ��������� ���Ե� Ŭ����,�ϳ��� �������� ����
	private int idx;			// 입력되는 순서대로 부여되는 일련번호
	private int ref;			// 게시글의 글번호
	private int lev;			// 답변의 레벨
	private int seq;			// 답변글의 일련번호
	private String fileName,folderName,fileExtension,wdate;
	private long fileSize;//���ϻ�����
	private MultipartFile uploadfile;//�������� ������ ���� MultipartFile ��ü

	
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getLev() {
		return lev;
	}

	public void setLev(int lev) {
		this.lev = lev;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public MultipartFile getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(MultipartFile uploadfile) {
		this.uploadfile = uploadfile;
	}

	@Override
	public String toString() {
		return "FileDTO [idx=" + idx + ", ref=" + ref + ", lev=" + lev + ", seq=" + seq + ", fileName=" + fileName
				+ ", folderName=" + folderName + ", fileExtension=" + fileExtension + ", wdate=" + wdate + ", fileSize="
				+ fileSize + ", uploadfile=" + uploadfile + "]";
	}
}
