import http from '@/services/httpService';
import axios from 'axios';

/**
 * @function
 * 모임 검색
 */
export const apiGetSearchList = async (keyword: string) => {
  try {
    return await http.get(`/api/v1/search/list?keyword=${keyword}`);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 모임 상세
 */
export const apiGetGroupInfo = async (id: number) => {
  try {
    return await http.get(`/api/v1/groups/info?groupId=${id}`);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 모임 삭제
 */
export const apiPostGroupDelete = async (data: {
  groupId: number;
  memberId: number;
}) => {
  try {
    return await http.post('/api/v1/groups/delete', {
      groupId: data.groupId,
      memberId: data.memberId,
    });
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 모임 상태 변경
 */
export const apiGetUpdateStatus = async (query: {
  memberId: number;
  groupId: number;
  status: string;
}) => {
  console.log('query update status api:', query);
  try {
    return await http.post('/api/v1/groups/update-status', {
      memberId: query.memberId,
      groupId: query.groupId,
      status: query.status,
    });
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 모임 참여자 리스트
 */
export const apiGetGroupAttendList = async (id: number) => {
  try {
    return await http.get(`/api/v1/group-attend/list?groupId=${id}`);
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};

/**
 * @function
 * 모임 참여하기
 */
export const apiPostAttendApply = async (id: number) => {
  try {
    return await http.post('/api/v1/group-attend/apply', {
      groupId: id,
    });
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
/**
 * @function
 * 모임 참여 취소하기
 */
export const apiPostAttendCancel = async (id: number) => {
  try {
    return await http.post('/api/v1/group-attend/cancel', {
      groupId: id,
    });
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
