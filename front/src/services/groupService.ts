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
 * TODO 모임을 삭제하는데 그룹 아이디 빼고 이 정보를 모두 입력하는게 맞나?? 메서드는 왜 delete 를 안쓰지?
 */
export const apiPostGroupDelete = async () => {
  try {
    return await http.post('/api/v1/groups/delete', {
      memberId: 0,
      groupId: 0,
      category: 'string',
      name: 'string',
      description: 'string',
      strStartDateTime: 'string',
      strEndDateTime: 'string',
      groupImage: 'string',
      groupMaxNum: 0,
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
    return await http.get(
      `/api/v1/groups/update-status?memberId=${query.memberId}&groupId=${query.groupId}&status=${query.status}`,
    );
  } catch (e) {
    if (axios.isAxiosError(e) && e.response) {
      throw e.response.data;
    }
  }
};
