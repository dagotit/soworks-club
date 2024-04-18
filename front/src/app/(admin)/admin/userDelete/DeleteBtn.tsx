'use client'

import React, { Fragment, memo } from 'react';
import { usePostMemberDelete } from '@/hooks/useAdmin';

interface useListProps {
  memberId: number | undefined;
  apiMemberDeleteSuccess: any;
  myMemberId: number | undefined;
}

// eslint-disable-next-line react/display-name
const DeleteBtn = memo((props: useListProps) => {
  // api
  const deleteMember = usePostMemberDelete();

  /**
   * @function
   * 회원 삭제하기 [ api ]
   */
  function onClickMemberDelete(id?: number) {
    deleteMember.mutate(id, {
      onSuccess: props.apiMemberDeleteSuccess,
      onError: (error) => {
        console.log('삭제 api 실패', error)
      }
    })
  }

  return <Fragment>
          {props.myMemberId !== props.memberId &&
            <button type="button" onClick={() => onClickMemberDelete(props.memberId)}>삭제</button>
          }
          {props.myMemberId === props.memberId &&
            <p>본인 삭제 불가</p>
          }
        </Fragment>
})

export default DeleteBtn;