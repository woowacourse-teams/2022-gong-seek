import React, { useState } from 'react';

import Card from '@/components/@common/Card/Card';
import * as S from '@/components/hashTag/HashTag/HashTag.styles';
import { WritingHashTagCardStyle } from '@/styles/cardStyle';
import { validatedHashTagInput } from '@/utils/validateInput';

export interface HashTagProps {
	hashTags: string[];
	setHashTags: React.Dispatch<React.SetStateAction<string[]>>;
}

const HashTag = ({ hashTags, setHashTags }: HashTagProps) => {
	const [hashTagInput, setHashInput] = useState<string>('');
	const [errorMsg, setErrorMsg] = useState<string>('');

	const checkIsDuplicateHashTag = (hashtag: string) =>
		hashTags.reduce((acc, cur) => {
			if (cur.toLowerCase() === hashtag.toLowerCase()) {
				acc = true;
			}
			return acc;
		}, false);

	const onHashTagEnterEventHandler = (e: React.ChangeEvent<HTMLFormElement>) => {
		e.preventDefault();
		if (hashTags.length === 5) {
			setErrorMsg('해시태그는 최대 5개까지 등록이 가능합니다');
			setHashInput('');
			return;
		}
		if (!validatedHashTagInput(hashTagInput.trim())) {
			setErrorMsg('2글자 이상 20이하여야 합니다');
			setHashInput('');
			return;
		}
		if (checkIsDuplicateHashTag(hashTagInput.trim())) {
			setErrorMsg('중복된 해시태그는 입력하실 수 없습니다');
			setHashInput('');
			return;
		}
		setHashTags([...hashTags, hashTagInput.trim()]);
		setErrorMsg('');
		setHashInput('');
	};

	const onHashTagDeleteEventHandler = (e: React.KeyboardEvent<HTMLInputElement>) => {
		if (e.code === 'Backspace' && hashTags.length >= 1 && hashTagInput.length === 0) {
			if (hashTags.length <= 1) {
				setHashTags([]);
				return;
			}

			setHashTags(hashTags.filter((item, index) => index < hashTags.length - 1));
			return;
		}
	};

	return (
		<S.Container>
			<S.HashTagForm
				aria-label="해시태그가 보여지고 입력하는 곳입니다"
				onSubmit={onHashTagEnterEventHandler}
			>
				<Card {...WritingHashTagCardStyle}>
					<S.HashTagItemBox>
						{hashTags.length >= 1 &&
							hashTags.map((item) => <S.HastTagItem key={item}>{item}</S.HastTagItem>)}
					</S.HashTagItemBox>
					<S.HastTagInput
						minLength={2}
						maxLength={20}
						aria-label="해시태그를 입력하는 곳입니다"
						placeholder="해시태그를 입력 후 엔터를 눌러주세요"
						value={hashTagInput}
						onChange={(e) => {
							setHashInput(e.target.value);
						}}
						onKeyDown={(e) => {
							onHashTagDeleteEventHandler(e);
						}}
					/>
				</Card>
				<S.ErrorMessage>{errorMsg}</S.ErrorMessage>
			</S.HashTagForm>
		</S.Container>
	);
};

export default HashTag;
