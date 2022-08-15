import { useEffect, useState } from 'react';

import HashTagSearchBox from '@/pages/HashTagSearch/HashTagSearchBox/HashTagSearchBox';
import HashTagSearchResult from '@/pages/HashTagSearch/HashTagSearchResult/HashTagSearchResult';
import useGetAllHashTags from '@/pages/HashTagSearch/hooks/useGetAllHashTags';
import useGetArticleByHashTag from '@/pages/HashTagSearch/hooks/useGetArticleByHashTag';
import * as S from '@/pages/HashTagSearch/index.styles';

const HashTagSearch = () => {
	const [targetHashTags, setTargetHashTags] = useState<{ name: string; isChecked: boolean }[]>([]);
	const {
		data: tagsOption,
		isSuccess: isTagsOptionSuccess,
		isLoading: isTagsOptionLoading,
	} = useGetAllHashTags();

	const {
		data: articles,
		isSuccess: isArticlesSuccess,
		isLoading: isArticleLoading,
		mutate,
	} = useGetArticleByHashTag();

	useEffect(() => {
		if (isTagsOptionSuccess && tagsOption && tagsOption?.tags.length >= 1) {
			setTargetHashTags(
				tagsOption.tags.map((item) => ({
					name: item,
					isChecked: false,
				})),
			);
		}
	}, [isTagsOptionSuccess]);

	useEffect(() => {
		const selectedHashTagList = targetHashTags.map((item) => {
			if (item.isChecked) {
				return item.name;
			}
			return;
		});
		if (selectedHashTagList && selectedHashTagList.length >= 1) {
			const tags = selectedHashTagList.join(',');
			mutate(tags);
		}
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
				{isArticleLoading && <S.EmptyMsg>게시글 조회 중</S.EmptyMsg>}
				{isArticlesSuccess && articles ? (
					<HashTagSearchResult articles={articles?.articles} />
				) : (
					<S.EmptyMsg>해시태그를 눌러주세요</S.EmptyMsg>
				)}
			</S.HashTagSearchResultContainer>
		</S.Container>
	);
};

export default HashTagSearch;
