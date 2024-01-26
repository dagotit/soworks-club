import React, {useEffect, useState} from 'react';
import { useDaumPostcodePopup } from 'react-daum-postcode';
import { Jua } from 'next/font/google';

const jua = Jua({weight: ["400"], subsets: ['latin']});
interface AddressSearchProps {
  onSelectAddress: (addressData: { selectedAddress: string; selectedZipCode: string }) => void;
}
const AddressSearch: React.FC<AddressSearchProps> = ({ onSelectAddress }) => {
  const scriptUrl= 'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
  const open = useDaumPostcodePopup(scriptUrl);

  const handleComplete = (data: any) => {
    let fullAddress = data.address;
    let zipCode = data.zonecode;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress += extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }
    onSelectAddress({ selectedAddress: fullAddress, selectedZipCode: zipCode });
  };

  const handleClick = () => {
    open({ onComplete: handleComplete });
  };

  return (
    <button className={jua.className} type='button' onClick={handleClick}>
      우편번호 검색
    </button>
  );
};

export default AddressSearch;