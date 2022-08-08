import React, { useState } from 'react';

import * as S from '@/components/common/HashTag/HashTag.styles';
import { validateHashTagInput } from '@/utils/validateInput';

export interface HashTagProps {
	hashTags: string[];
	setHashTags: React.Dispatch<React.SetStateAction<string[]>>;
}

const HashTag = ({ hashTags, setHashTags }: HashTagProps) => {
	const [hashTagInput, setHashInput] = useState<string>('');
	const [errorMsg, setErrorMsg] = useState<string>('');

	const checkIsDuplicateHashTag = () => hashTags.includes(hashTagInput);

	const onHashTagEnterEventHandler = (e: React.ChangeEvent<HTMLFormElement>) => {
		e.preventDefault();
		if (hashTags.length === 5) {
			setErrorMsg('해시태그는 최대 5개까지 등록이 가능합니다');
			setHashInput('');
			return;
		}
		if (!validateHashTagInput(hashTagInput)) {
			setErrorMsg('2글자 이상 20이하여야 합니다');
			setHashInput('');
			return;
		}
		if (checkIsDuplicateHashTag()) {
			setErrorMsg('중복된 해시태그는 입력하실 수 없습니다');
			setHashInput('');
			return;
		}
		setHashTags([...hashTags, hashTagInput]);
		setErrorMsg('');
		setHashInput('');
	};

	return (
		<S.Container>
			<S.HashTagForm
				aria-label="해시태그가 보여지고 입력하는 곳입니다"
				onSubmit={onHashTagEnterEventHandler}
			>
				{hashTags.length >= 1 &&
					hashTags.map((item) => <S.HastTagItem key={item}>{item}</S.HastTagItem>)}
				<S.HastTagInput
					aria-label="해시태그를 입력하는 곳입니다"
					placeholder="해시태그를 입력해주세요"
					value={hashTagInput}
					onChange={(e) => {
						setHashInput(e.target.value);
					}}
				/>
			</S.HashTagForm>
			<S.ErrorMessage>{errorMsg}</S.ErrorMessage>
		</S.Container>
	);
};

export default HashTag;
