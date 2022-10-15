import useCarousel from '../../../hooks/common/useCarousel';
import { Category } from '../../../types/articleResponse';
import PopularArticleItem from '../PopularArticleItem/PopularArticleItem';
import { useNavigate } from 'react-router-dom';

import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import ArticleItem from '@/components/article/ArticleItem/ArticleItem';
import * as S from '@/components/article/PopularArticle/PopularArticle.styles';
import useGetPopularArticles from '@/hooks/article/useGetPopularArticles';

const PopularArticle = () => {
	const { carouselElement, handleLeftSlideEvent, handleRightSlideEvent, initCarousel } =
		useCarousel();
	const { data, isLoading } = useGetPopularArticles(initCarousel);
	const navigate = useNavigate();

	if (isLoading) {
		return <Loading />;
	}

	if (!data?.articles.length) {
		return <EmptyMessage>게시글이 존재하지 않습니다</EmptyMessage>;
	}

	const handleClickArticleItem = ({ id, category }: { id: string; category: Category }) => {
		navigate(`/articles/${category}/${id}`);
	};

	return data ? (
		<S.Container>
			<S.LeftArrowButton onClick={handleLeftSlideEvent} />
			<S.ArticleContent ref={carouselElement}>
				{data.articles.map((article) => (
					<PopularArticleItem article={article} key={article.id} />
				))}
			</S.ArticleContent>
			<S.RightArrowButton onClick={handleRightSlideEvent} />
		</S.Container>
	) : null;
};

export default PopularArticle;
