import useCarousel from '../../../hooks/common/useCarousel';
import { Category } from '../../../types/articleResponse';
import PopularArticleItem from '../PopularArticleItem/PopularArticleItem';
import { useNavigate } from 'react-router-dom';

import Card from '@/components/@common/Card/Card';
import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import * as S from '@/components/article/PopularArticle/PopularArticle.styles';
import useGetPopularArticles from '@/hooks/article/useGetPopularArticles';
import { PopularArticleItemCardStyle } from '@/styles/cardStyle';

const PopularArticle = () => {
	const { handleCarouselElementRef, handleLeftSlideEvent, handleRightSlideEvent, currentIndex } =
		useCarousel();
	const { data, isLoading } = useGetPopularArticles();
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
		<S.Container ref={handleCarouselElementRef}>
			<S.LeftArrowButton onClick={handleLeftSlideEvent} />
			<S.ArticleContent>
				<Card {...PopularArticleItemCardStyle} isActive={false}></Card>
				{data.articles.map((article, idx) => (
					<PopularArticleItem article={article} key={article.id} isActive={currentIndex === idx} />
				))}
				<Card {...PopularArticleItemCardStyle} isActive={false}></Card>
			</S.ArticleContent>
			<S.RightArrowButton onClick={handleRightSlideEvent} />
		</S.Container>
	) : null;
};

export default PopularArticle;
