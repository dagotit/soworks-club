'use client';

import { clubListProps } from '@/components/calendar/List';
import { isEmptyObj } from '@/utils/common';
import styles from './ClubListFilter.module.css';

const ClubListFilter = (props: clubListProps) => {
  /**
   * @function
   * 팝업 닫기 버튼
   */
  function handleClose() {
    if (!isEmptyObj(props)) {
      props.popupState(false);
    }
  }
  return (
    <div className={styles.filterWrap}>
      <div className={styles.filter}>
        <div className={styles.infoWrap}></div>
        <div className={styles.actionWrap}>
          <button type="button" onClick={handleClose}>
            닫기
          </button>
        </div>
      </div>
    </div>
  );
};

export default ClubListFilter;
