import EmptyMessage from '@/components/common/EmptyMessage/EmptyMessage';
import Loading from '@/components/common/Loading/Loading';
import useGetUserArticles from '@/hooks/user/useGetUserArticles';
import * as S from '@/pages/MyPage/UserArticleBox/UserArticleBox.styles';
import UserArticleItem from '@/pages/MyPage/UserArticleItem/UserArticleItem';

const UserArticleBox = () => {
	const {
		data: articles,
		isSuccess: isArticlesSuccess,
		isLoading: isArticlesLoading,
	} = useGetUserArticles();

	if (isArticlesLoading) {
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
						<EmptyMessage>작성하신 글이 없습니다</EmptyMessage>
					)}
				</S.Container>
			) : (
				<div>정보를 가져오는데 실패하였습니다</div>
			)}
		</>
	);
};

export default UserArticleBox;
