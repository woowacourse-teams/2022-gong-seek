import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	align-items: center;
	justify-content: center;

	width: 100%;
	min-height: 100%;
`;

export const HashTagSearchBox = styled.div`
	width: 100%;
	margin-bottom: ${({ theme }) => theme.size.SIZE_040};
`;

export const HashTagSelectTitle = styled.div`
	width: 100%;
	text-align: center;

	margin-bottom: ${({ theme }) => theme.size.SIZE_030};
`;

export const HashTagSearchBoxContainer = styled.div`
	width: 80%;

	margin-bottom: ${({ theme }) => theme.size.SIZE_020};
`;

export const HashTagSearchResultContainer = styled.div`
	display: flex;
	width: 100%;

	justify-content: center;
`;

export const EmptyMsg = styled.div``;
