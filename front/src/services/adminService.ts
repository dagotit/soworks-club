import axios from 'axios';
import http from '@/services/httpService';

/**
 * @function
 * 어드민 계정인지 체크
 */
export const apiGetAdminCheck = async () => {
  try {
    return await http.get('/api/v1/member/check-admin');
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 이메일로 회원 리스트 조회?
 */
export const apiGetMemberEmailFind = async (email: string | null) => {
  try {
    return await http.get(`/api/v1/admin/${email}`);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 회원일괄조회
 */
export const apiGetMemberList = async (name ?: string | null) => {
  try {
    let url = '/api/v1/admin/member-list';
    if (name) {
      url += `?name=${name}`
    }
    return await http.get(url);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
}

/**
 * @function
 * 회원 삭제
 */
export const apiPostMemberDelete = async (id?: number| null) => {
  try {
    if (!id) {
      throw new Error('id undefined');
    }
    return await http.post('/api/v1/admin/member-delete', {
      memberId: id,
    });
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 회원 일괄 업로드
 * file multipart / form-data
 */
export const apiPostMemberUpload = async (data: {
  formData: FormData;
  progress: any;
}) => {
  try {
    // file
    return await http.post('/api/v1/admin/member-upload', data.formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
      onUploadProgress: data.progress,
    });
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 일괄 업로드 파일 다운로드
 */
export const apiPostTemplateDownLoad = async (data: null) => {
  try {
    return await http.post('/api/v1/admin/template', null, {"responseType" : "blob"})
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
}

/**
 * @function
 *  알람보내기
 */
export const apiGetNoticeAlarm = async (id: number) => {
  try {
    // TODO 알람 내용도 등록가능?
    return await http.get(`/api/v1/admin/send-alarm?memberId=${id}`);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
