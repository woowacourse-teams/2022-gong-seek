import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import HashTagClickSearchBox from '@/components/hashTag/HashTagClickSearchBox/HashTagClickSearchBox';
import HashTagSearchResult from '@/components/hashTag/HashTagSearchResult/HashTagSearchResult';
import useHandleHashTagState from '@/hooks/hashTag/useHandleHashTagState';
import * as S from '@/pages/HashTagSearch/index.styles';

const HashTagSearch = () => {
	const {
		isTagsOptionLoading,
		isTagsOptionSuccess,
		targetHashTags,
		selectedHashTags,
		setTargetHashTags,
	} = useHandleHashTagState();
	return (
		<S.Container>
			<S.HashTagSelectTitle>찾고 싶은 해시태그를 클릭해주세요 🔍</S.HashTagSelectTitle>

			<S.HashTagSearchBoxContainer>
				{isTagsOptionLoading && <S.EmptyMsg>해시태그 조회 중</S.EmptyMsg>}
				{isTagsOptionSuccess && (
					<HashTagClickSearchBox targets={targetHashTags} setTargets={setTargetHashTags} />
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
