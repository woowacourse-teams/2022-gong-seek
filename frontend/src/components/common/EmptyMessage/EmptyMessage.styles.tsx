import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;

	width: 100%;
	height: 60vh;
`;

export const EmptyImg = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_050};
`;

export const EmptyDescription = styled.div`
	margin-top: ${({ theme }) => theme.size.SIZE_020};
`;
