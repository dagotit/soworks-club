'use client';

import styles from './Dialog.module.css';
import { useDialogStore } from '@/store/useDialog';
import { useMemo } from 'react';
import Dialog from './Dialog';

const DialogWrap = () => {
  const { dialogList } = useDialogStore();
  const componentDialog = useMemo(() => {
    return <Dialog />;
  }, [dialogList]);

  return <div className={styles.dialogWrap}>{componentDialog}</div>;
};

export default DialogWrap;
