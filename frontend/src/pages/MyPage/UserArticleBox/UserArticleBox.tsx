import Loading from '@/components/common/Loading/Loading';
import useGetUserArticles from '@/hooks/user/useGetUserArticles';
import * as S from '@/pages/MyPage/UserArticleBox/UserArticleBox.styles';
import UserArticleItem from '@/pages/MyPage/UserArticleItem/UserArticleItem';

const UserArticleBox = () => {
	const {
		data: articles,
		isSuccess: isArticlesSuccess,
		isLoading: isArticlesLoading,
		isIdle: isArticlesIdle,
	} = useGetUserArticles();
	if (isArticlesLoading || isArticlesIdle) {
		return <Loading />;
	}
	return (
		<>
			{isArticlesSuccess ? (
				<S.Container>
					{articles ? (
						articles.articles.map((article) => (
							<UserArticleItem key={article.id} article={article} />
						))
					) : (
						<S.EmptyMsg>작성하신 글이 없습니다</S.EmptyMsg>
					)}
				</S.Container>
			) : (
				<div>정보를 가져오는데 실패하였습니다</div>
			)}
		</>
	);
};

export default UserArticleBox;
