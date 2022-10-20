import React, { useEffect, useState } from 'react';

import HashTagClickSearchBox from '@/components/hashTag/HashTagClickSearchBox/HashTagClickSearchBox';
import * as S from '@/components/hashTag/HashTagSearchBox/HashTagSearchBox.styles';

export interface HashTagSearchBoxProps {
	targets: { name: string; isChecked: boolean }[];
	setTargets: React.Dispatch<React.SetStateAction<{ name: string; isChecked: boolean }[]>>;
}

const isHashTagSearchResultType = (
	searchResult: unknown,
): searchResult is HashTagSearchBoxProps['targets'] => true;

const HashTagSearchBox = ({ targets, setTargets }: HashTagSearchBoxProps) => {
	const [hashTagSearchText, setHashTagSearchText] = useState('');
	const [hashTagSearchResult, setHashTagSearchResult] = useState<
		HashTagSearchBoxProps['targets'] | []
	>([]);
	const [hashTagResultDescription, setHashTagResultDescription] = useState('');

	useEffect(() => {
		searchTargetHashTag();
	}, [hashTagSearchText]);

	const handleChangeHashTagSearchInput = (e: React.ChangeEvent<HTMLInputElement>) => {
		setHashTagSearchText(e.target.value);
	};

	const handleSubmitHashTagSearch = () => {
		setHashTagSearchText('');
		searchTargetHashTag();
	};

	const searchTargetHashTag = () => {
		if (hashTagSearchText.length === 0) {
			setHashTagSearchResult([]);
			return;
		}
		if (targets.length === 0) {
			setHashTagResultDescription('해시태그가 존재하지 않습니다');
			setHashTagSearchResult([]);
		}
		if (targets.length >= 1) {
			const searchedHashTags = targets.filter((item) => {
				const names = item.name.trim();
				if (names.includes(hashTagSearchText)) {
					return item;
				}
			});
			if (searchedHashTags.length < 1) {
				setHashTagResultDescription('해시태그가 존재하지 않습니다');
				setHashTagSearchResult([]);
				return;
			}
			setHashTagSearchResult(searchedHashTags);
		}
	};
	return (
		<S.Container>
			<S.SearchBarBox
				onSubmit={(e) => {
					e.preventDefault();
					handleSubmitHashTagSearch();
				}}
			>
				<S.SearchBar
					role="search"
					placeholder="찾고 싶으신 해시태그를 입력해주세요"
					value={hashTagSearchText}
					onChange={handleChangeHashTagSearchInput}
				/>
				<S.SearchButtonBox type="button">
					<S.SearchButton
						role="button"
						aria-label="해시태그가 존재하는지 검색합니다"
						onClick={handleSubmitHashTagSearch}
					/>
				</S.SearchButtonBox>
			</S.SearchBarBox>
			<S.HashTagListBox>
				{hashTagSearchResult.length < 1 && (
					<S.HashTagSearchResultDescription>
						{hashTagResultDescription}
					</S.HashTagSearchResultDescription>
				)}
				{hashTagSearchResult.length >= 1 &&
					typeof hashTagSearchResult !== 'string' &&
					isHashTagSearchResultType(hashTagSearchResult) && (
						<HashTagClickSearchBox
							targets={hashTagSearchResult}
							setTargets={setTargets}
							setSelectedTargets={setHashTagSearchResult}
						/>
					)}
			</S.HashTagListBox>
		</S.Container>
	);
};

export default HashTagSearchBox;
