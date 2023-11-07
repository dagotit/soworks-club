'use client';

import { useDialogStore } from '@/store/useDialog';
import { useMemo } from 'react';
import Dialog from './Dialog';

const DialogWrap = () => {
  const { dialogList } = useDialogStore();
  const componentDialog = useMemo(() => {
    return <Dialog />;
  }, [dialogList]);

  return <div className="dialog-wrap">{componentDialog}</div>;
};

export default DialogWrap;
