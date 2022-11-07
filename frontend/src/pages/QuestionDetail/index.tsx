import { Suspense } from 'react';
import { useParams } from 'react-router-dom';

import Loading from '@/components/@common/Loading/Loading';
import useGetDetailArticle from '@/hooks/article/useGetDetailArticle';
import Detail from '@/pages/Detail/index';

const QuestionDetail = () => {
	const { id } = useParams();

	if (typeof id === 'undefined') {
		throw new Error('id 값을 받아오지 못했습니다');
	}

	const { data } = useGetDetailArticle(id);

	return (
		<Suspense fallback={<Loading />}>
			{data && <Detail article={data} articleId={id} category="질문" />}
		</Suspense>
	);
};

export default QuestionDetail;
