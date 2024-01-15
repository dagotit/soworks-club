'use client';

import { isEmptyObj } from '@/utils/common';
import styles from './ClubListFilter.module.css';
import { memo, useEffect, useState } from 'react';
import { FilterQueryParamType } from '@/hooks/useCalendar';

interface clubFilterProps {
  popupState: any;
  popupApplyData: any;
  listFilterQueryData: FilterQueryParamType | {};
}

const ClubListFilter = memo((props: clubFilterProps) => {
  const [allCheckBox, setAllCheckBox] = useState(false);
  const [attendClubCheckBox, setAttendClubCheckBox] = useState(false);
  const [createClubCheckBox, setCreateClubCheckBox] = useState(false);

  /**
   * @function
   * 팝업을 열었을 때 이미 필터 적용이 있다면 값을 셋팅
   */
  useEffect(() => {
    if (!isEmptyObj(props.listFilterQueryData)) {
      const { isAll, isAttendClub, isCreateClub } = {
        ...props.listFilterQueryData,
      };
      if (isAll !== undefined) {
        setAllCheckBox(isAll);
      }
      if (isAttendClub !== undefined) {
        setAttendClubCheckBox(isAttendClub);
      }
      if (isCreateClub !== undefined) {
        setCreateClubCheckBox(isCreateClub);
      }
    }
  }, []);
  /**
   * @function
   * 팝업 닫기 버튼
   */
  function handleClose() {
    if (!isEmptyObj(props)) {
      props.popupState(false);
    }
  }
  /**
   * @function
   * 필터내용 적용하기
   */
  function handleApply() {
    if (!isEmptyObj(props)) {
      props.popupApplyData({
        isAll: allCheckBox,
        startDate: '',
        endDate: '',
        isAttendClub: attendClubCheckBox,
        isCreateClub: createClubCheckBox,
      });
    }
  }
  return (
    <div className={styles.filterWrap}>
      <div className={styles.filter}>
        <div className={styles.closeWrap}>
          <button type="button" onClick={handleClose}>
            닫기
          </button>
        </div>
        <div className={styles.infoWrap}>
          <div className={styles.filterInputWrap}>
            <span>전체보기</span>
            <div>
              <input
                type="checkbox"
                id="fe_club_filter1"
                className={styles.switch}
                checked={allCheckBox}
                onChange={(e) => setAllCheckBox(e.currentTarget.checked)}
              />
              <label htmlFor="fe_club_filter1" className={styles.switch_label}>
                <span className={styles.onf_btn}></span>
              </label>
            </div>
          </div>

          <label>
            <span>날짜로 보기</span>
            <div className={styles.filterDateWrap}>
              <input type="date" /> ~ <input type="date" />
            </div>
          </label>

          <div className={styles.filterInputWrap}>
            <span>내가 참여한 모임만 보기</span>
            <div>
              <input
                type="checkbox"
                id="fe_club_filter2"
                className={styles.switch}
                checked={attendClubCheckBox}
                onChange={(e) => setAttendClubCheckBox(e.currentTarget.checked)}
              />
              <label htmlFor="fe_club_filter2" className={styles.switch_label}>
                <span className={styles.onf_btn}></span>
              </label>
            </div>
          </div>

          <div className={styles.filterInputWrap}>
            <span>내가 만든 모임만 보기</span>
            <div>
              <input
                type="checkbox"
                id="fe_club_filter3"
                className={styles.switch}
                checked={createClubCheckBox}
                onChange={(e) => setCreateClubCheckBox(e.currentTarget.checked)}
              />
              <label htmlFor="fe_club_filter3" className={styles.switch_label}>
                <span className={styles.onf_btn}></span>
              </label>
            </div>
          </div>
        </div>
        <div className={styles.actionWrap}>
          <button type="button" onClick={handleApply}>
            적용하기
          </button>
        </div>
      </div>
      <div className={styles.filterDim}></div>
    </div>
  );
});

export default ClubListFilter;
