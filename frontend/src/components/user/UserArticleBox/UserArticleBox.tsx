import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import * as S from '@/components/user/UserArticleBox/UserArticleBox.styles';
import UserArticleItem from '@/components/user/UserArticleItem/UserArticleItem';
import useGetUserArticles from '@/hooks/user/useGetUserArticles';

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
