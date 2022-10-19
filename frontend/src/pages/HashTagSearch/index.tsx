import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import HashTagClickSearchBox from '@/components/hashTag/HashTagClickSearchBox/HashTagClickSearchBox';
import HashTagSearchBox from '@/components/hashTag/HashTagSearchBox/HashTagSearchBox';
import HashTagSearchResult from '@/components/hashTag/HashTagSearchResult/HashTagSearchResult';
import useHandleHashTagState from '@/hooks/hashTag/useHandleHashTagState';
import * as S from '@/pages/HashTagSearch/index.styles';

const HashTagSearch = () => {
	const {
		isTagsOptionLoading,
		isTagsOptionSuccess,
		totalHashTags,
		selectedHashTags,
		setTargetHashTags,
		setTotalHashTags,
	} = useHandleHashTagState();
	return (
		<S.Container>
			<S.HashTagSearchBox>
				<HashTagSearchBox targets={totalHashTags} setTargets={setTargetHashTags} />
			</S.HashTagSearchBox>

			<S.HashTagSelectTitle>전체 해시태그 살펴보기</S.HashTagSelectTitle>

			<S.HashTagSearchBoxContainer>
				{isTagsOptionLoading && <S.EmptyMsg>해시태그 조회 중</S.EmptyMsg>}
				{isTagsOptionSuccess && (
					<HashTagClickSearchBox targets={totalHashTags} setTargets={setTotalHashTags} />
				)}
			</S.HashTagSearchBoxContainer>

			<S.HashTagSearchResultContainer>
				{selectedHashTags && selectedHashTags.length >= 1 ? (
					<HashTagSearchResult hashTags={selectedHashTags} />
				) : (
					<EmptyMessage>해시태그를 눌러주세요</EmptyMessage>
				)}
			</S.HashTagSearchResultContainer>
		</S.Container>
	);
};

export default HashTagSearch;
