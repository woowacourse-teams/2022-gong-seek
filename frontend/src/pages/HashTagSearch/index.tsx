import { useEffect, useState } from 'react';

import HashTagSearchBox from '@/pages/HashTagSearch/HashTagSearchBox/HashTagSearchBox';
import HashTagSearchResult from '@/pages/HashTagSearch/HashTagSearchResult/HashTagSearchResult';
import useGetAllHashTags from '@/pages/HashTagSearch/hooks/useGetAllHashTags';
import * as S from '@/pages/HashTagSearch/index.styles';

const HashTagSearch = () => {
	const [targetHashTags, setTargetHashTags] = useState<{ name: string; isChecked: boolean }[]>([]);
	const [selectedHashTags, setSelectedHashTags] = useState<string[]>([]);
	const {
		data: tagsOption,
		isSuccess: isTagsOptionSuccess,
		isLoading: isTagsOptionLoading,
	} = useGetAllHashTags();

	useEffect(() => {
		if (isTagsOptionSuccess && tagsOption && tagsOption?.tag.length >= 1) {
			setTargetHashTags(
				tagsOption.tag.map((item) => ({
					name: item,
					isChecked: false,
				})),
			);
		}
	}, [isTagsOptionSuccess]);

	useEffect(() => {
		setSelectedHashTags(targetHashTags.filter((item) => item.isChecked).map((item) => item.name));
	}, [targetHashTags]);

	return (
		<S.Container>
			<S.HashTagSelectTitle>찾고 싶은 해시태그를 클릭해주세요 🔍</S.HashTagSelectTitle>

			<S.HashTagSearchBoxContainer>
				{isTagsOptionLoading && <S.EmptyMsg>해시태그 조회 중</S.EmptyMsg>}
				{isTagsOptionSuccess && (
					<HashTagSearchBox targets={targetHashTags} setTargets={setTargetHashTags} />
				)}
			</S.HashTagSearchBoxContainer>

			<S.HashTagSearchResultContainer>
				{selectedHashTags && selectedHashTags.length >= 1 ? (
					<HashTagSearchResult hashTags={selectedHashTags} />
				) : (
					<S.EmptyMsg>해시태그를 눌러주세요</S.EmptyMsg>
				)}
			</S.HashTagSearchResultContainer>
		</S.Container>
	);
};

export default HashTagSearch;
