'use client';

import { isEmptyObj } from '@/utils/common';
import styles from './ClubListFilter.module.css';
import React, { memo, useEffect, useState } from 'react';
import { FilterQueryParamType } from '@/hooks/useCalendar';
import { DateTime } from 'luxon';

interface clubFilterProps {
  popupState: any;
  popupApplyData: any;
  listFilterQueryData: FilterQueryParamType | {};
}

const ClubListFilter = memo((props: clubFilterProps) => {
  const DEFAULTENDDAY = `${DateTime.now().year}-${
    String(DateTime.now().month).length === 1
      ? '0' + DateTime.now().month
      : DateTime.now().month
  }-${new Date(DateTime.now().year, DateTime.now().month, 0).getDate()}`; // 모임리스트 조회 종료일
  const DEFAULTSTARTDAY = DateTime.now().toISODate(); // 모임리스트 조회 시작일
  const [allCheckBox, setAllCheckBox] = useState(true);
  const [attendClubCheckBox, setAttendClubCheckBox] = useState(false);
  const [createClubCheckBox, setCreateClubCheckBox] = useState(false);
  const [startDay, setStartDay] = useState(DEFAULTSTARTDAY);
  const [endDay, setEndDay] = useState(DEFAULTENDDAY);
  const [statusClubCheckBox, setStatusClubCheckBox] = useState(true);

  /**
   * @function
   * 팝업을 열었을 때 이미 필터 적용이 있다면 값을 셋팅
   */
  useEffect(() => {
    if (!isEmptyObj(props.listFilterQueryData)) {
      const {
        isAll,
        isAttendClub,
        isCreateClub,
        startDate,
        endDate,
        statusClub,
      } = {
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
      if (startDate !== undefined) {
        setStartDay(startDate);
      }
      if (endDate !== undefined) {
        setEndDay(endDate);
      }
      if (statusClub !== undefined) {
        setStatusClubCheckBox(statusClub);
      }
    }
  }, []);
  /**
   * @function
   * 시작날짜 onchange
   */
  function onChangeStartDate(e: React.ChangeEvent<HTMLInputElement>) {
    setStartDay(e.currentTarget.value);
    handleDetailChecked();
  }
  /**
   * @function
   * 시작날짜 onchange
   */
  function onChangeEndDate(e: React.ChangeEvent<HTMLInputElement>) {
    setEndDay(e.currentTarget.value);
    handleDetailChecked();
  }
  /**
   * @function
   * 전체 보기와 상세필터를 구분
   */
  function handleAllChecked() {
    if (!allCheckBox) {
      setStartDay(DEFAULTSTARTDAY);
      setEndDay(DEFAULTENDDAY);
      setAttendClubCheckBox(false);
      setCreateClubCheckBox(false);
      // setStatusClubCheckBox(true);
    }
  }
  /**
   * @function
   * 필터 있을 경우 > 전체조회 false
   * */
  function handleDetailChecked() {
    if (startDay !== DEFAULTENDDAY || endDay !== DEFAULTENDDAY) {
      setAllCheckBox(false);
    }

    if (!attendClubCheckBox || !createClubCheckBox) {
      setAllCheckBox(false);
    }
  }
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
        startDate: startDay,
        endDate: endDay,
        isAttendClub: attendClubCheckBox,
        isCreateClub: createClubCheckBox,
        statusClub: statusClubCheckBox,
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
                onChange={(e) => {
                  setAllCheckBox(e.currentTarget.checked);
                  handleAllChecked();
                }}
              />
              <label htmlFor="fe_club_filter1" className={styles.switch_label}>
                <span className={styles.onf_btn}></span>
              </label>
            </div>
          </div>

          <div className={styles.filterInputWrap}>
            <span>진행중인 모임</span>
            <div>
              <input
                type="checkbox"
                id="fe_club_filter2"
                className={styles.switch}
                checked={statusClubCheckBox}
                onChange={(e) => {
                  setStatusClubCheckBox(e.currentTarget.checked);
                }}
              />
              <label htmlFor="fe_club_filter2" className={styles.switch_label}>
                <span className={styles.onf_btn}></span>
              </label>
            </div>
          </div>

          <label>
            <span>날짜로 보기</span>
            <div className={styles.filterDateWrap}>
              <input
                type="date"
                value={startDay}
                onChange={onChangeStartDate}
              />
              ~ <input type="date" value={endDay} onChange={onChangeEndDate} />
            </div>
          </label>

          <div className={styles.filterInputWrap}>
            <span>내가 참여한 모임만 보기</span>
            <div>
              <input
                type="checkbox"
                id="fe_club_filter3"
                className={styles.switch}
                checked={attendClubCheckBox}
                onChange={(e) => {
                  setAttendClubCheckBox(e.currentTarget.checked);
                  handleDetailChecked();
                }}
              />
              <label htmlFor="fe_club_filter3" className={styles.switch_label}>
                <span className={styles.onf_btn}></span>
              </label>
            </div>
          </div>

          <div className={styles.filterInputWrap}>
            <span>내가 만든 모임만 보기</span>
            <div>
              <input
                type="checkbox"
                id="fe_club_filter4"
                className={styles.switch}
                checked={createClubCheckBox}
                onChange={(e) => {
                  setCreateClubCheckBox(e.currentTarget.checked);
                  handleDetailChecked();
                }}
              />
              <label htmlFor="fe_club_filter4" className={styles.switch_label}>
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
