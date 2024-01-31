import { memo, useCallback, useState } from 'react';

const CustomEvent = memo((props: any) => {
  // const { slotStart } = props;
  console.log('label:', props.slotStart);

  return <div className=""></div>;
});

export default CustomEvent;
