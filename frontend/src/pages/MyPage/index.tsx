import Loading from '@/components/common/Loading/Loading';
import useGetUserArticles from '@/hooks/user/useGetUserArticles';
import useGetUserComments from '@/hooks/user/useGetUserComments';
import useGetUserInfo from '@/hooks/user/useGetUserInfo';
import UserArticleItem from '@/pages/MyPage/UserArticleItem/UserArticleItem';
import UserCommentBox from '@/pages/MyPage/UserCommentBox/UserCommentBox';
import UserItemBox from '@/pages/MyPage/UserItemBox/UserItemBox';
import UserProfile from '@/pages/MyPage/UserProfile/UserProfile';
import * as S from '@/pages/MyPage/index.styles';

const MyPage = () => {
	//isIdle 제거, 어짜피 로딩막기 안됨!
	const {
		data: info,
		isSuccess: isInfoSuccess,
		isLoading: isInfoLoading,
		isIdle: isInfoIdle,
	} = useGetUserInfo();
	const {
		data: articles,
		isSuccess: isArticlesSuccess,
		isLoading: isArticlesLoading,
		isIdle: isArticlesIdle,
	} = useGetUserArticles();
	const {
		data: comments,
		isSuccess: isCommentsSuccess,
		isLoading: isCommentsLoading,
		isIdle: isCommentsIdle,
	} = useGetUserComments();

	if (
		isInfoLoading ||
		isArticlesLoading ||
		isCommentsLoading ||
		isInfoIdle ||
		isArticlesIdle ||
		isCommentsIdle
	) {
		return <Loading />;
	}

	return (
		<S.Container>
			<S.Title>마이 페이지</S.Title>
			{/* TODO: 3개의 성공 상태를 하나의 성공으로 묶자 */}
			{isInfoSuccess && isArticlesSuccess && isCommentsSuccess ? (
				<S.ContentContainer>
					{info && <UserProfile name={info.name} avatarUrl={info.avatarUrl} />}
					{/*TODO: map돌리는 부분은 하나의 List 컴포넌트로 분리하자 */}

					<UserItemBox subTitle="내가 작성한 글">
						{articles ? (
							articles.articles.map((article) => (
								<UserArticleItem key={article.id} article={article} />
							))
						) : (
							<div>작성하신 글이 없습니다</div>
						)}
					</UserItemBox>
					{/* TODO: 역시 map돌리는것은 하나로 묶자 */}
					<UserItemBox subTitle="내가 작성한 댓글">
						{comments ? (
							comments.comments.map((comment) => (
								<UserCommentBox key={comment.id} comment={comment} />
							))
						) : (
							<div>작성하신 댓글이 없습니다</div>
						)}
					</UserItemBox>
				</S.ContentContainer>
			) : (
				<div>정보를 가져오는데 실패하였습니다</div>
			)}
		</S.Container>
	);
};

export default MyPage;
