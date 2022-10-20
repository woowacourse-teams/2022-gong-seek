import { PropsWithOptionalChildren } from 'gongseek-types';
import { useParams } from 'react-router-dom';

import ArticleContent from '@/components/article/ArticleContent/ArticleContent';
import CommentContent from '@/components/comment/CommentContent/CommentContent';
import * as S from '@/pages/Detail/index.styles';
import { ArticleType } from '@/types/articleResponse';
import { CommentType } from '@/types/commentResponse';

export interface DetailProps {
	article: ArticleType;
	commentList: CommentType[];
	articleId: string;
	category: string;
}

const Detail = ({
	children,
	article,
	commentList,
	articleId,
	category,
}: PropsWithOptionalChildren<DetailProps>) => {
	const { id } = useParams();

	if (typeof id === 'undefined') {
		throw new Error('글을 찾지 못하였습니다.');
	}

	return (
		<S.Container tabIndex={0}>
			<ArticleContent
				article={article}
				author={article.author}
				category={category}
				articleId={articleId}
			/>
			{children}
			<CommentContent articleId={id} commentList={commentList} />
		</S.Container>
	);
};

export default Detail;
