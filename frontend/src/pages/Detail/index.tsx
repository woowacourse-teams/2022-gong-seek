import { PropsWithOptionalChildren } from 'gongseek-types';

import { ArticleTotalType } from '@/api/article/articleType';
import ArticleContent from '@/components/article/ArticleContent/ArticleContent';
import CommentContent from '@/components/comment/CommentContent/CommentContent';
import useScrollToTop from '@/hooks/common/useScrollToTop';
import * as S from '@/pages/Detail/index.styles';

export interface DetailProps {
	article: Omit<ArticleTotalType, 'updatedAt' | 'category' | 'commentCount'>;
	articleId: string;
	category: string;
}

const Detail = ({
	article,
	children,
	articleId,
	category,
}: PropsWithOptionalChildren<DetailProps>) => {
	useScrollToTop();

	return (
		<S.Container>
			<ArticleContent article={article} category={category} articleId={articleId} />
			{children}
			<CommentContent articleId={articleId} />
		</S.Container>
	);
};

export default Detail;
