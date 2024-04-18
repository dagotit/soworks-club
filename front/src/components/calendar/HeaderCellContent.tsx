import { memo, useCallback, useState } from 'react';

// eslint-disable-next-line react/display-name
const HeaderCellContent = memo((props: any) => {
  const { label } = props;

  function text(): string {
    return label;
  }
  return <span className="test">{label}</span>;
});

export default HeaderCellContent;
