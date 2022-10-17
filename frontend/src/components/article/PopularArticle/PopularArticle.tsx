import useCarousel from '../../../hooks/common/useCarousel';
import { Category } from '../../../types/articleResponse';
import PopularArticleItem from '../PopularArticleItem/PopularArticleItem';
import { useNavigate } from 'react-router-dom';

import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import * as S from '@/components/article/PopularArticle/PopularArticle.styles';
import useGetPopularArticles from '@/hooks/article/useGetPopularArticles';

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
		<S.Container ref={handleCarouselElementRef} role="tablist">
			<S.LeftArrowButton aria-label="이전" aria-disabled={currentIndex === 0}>
				<S.LeftArrowIcon onClick={handleLeftSlideEvent} />
			</S.LeftArrowButton>
			<S.ArticleContent>
				<PopularArticleItem article={data.articles[data.articles.length - 1]} isActive={false} />
				{data.articles.map((article, idx) => (
					<PopularArticleItem
						article={article}
						key={article.id}
						isActive={currentIndex === idx}
						onClick={() =>
							handleClickArticleItem({ id: String(article.id), category: article.category })
						}
						rightSlide={handleRightSlideEvent}
					/>
				))}
				<PopularArticleItem article={data.articles[0]} isActive={false} />
			</S.ArticleContent>
			<S.RightArrowButton aria-label="다음">
				<S.RightArrowIcon onClick={handleRightSlideEvent} />
			</S.RightArrowButton>
		</S.Container>
	) : null;
};

export default PopularArticle;
