import RecetteCard from 'app/modules/Recette/RecetteCard';
import React, { useEffect, useState } from 'react';
import { useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/recette/recette.reducer';
import { useDispatch } from 'react-redux';

export default function ListRecette() {
  const dispatch = useDispatch();
  const [recettes, setRecettes] = useState([]);

  useEffect(() => {
    // Dispatch the getEntities action when the component mounts
    const result = dispatch<any>(getEntities({}));

    result.then(data => setRecettes(data.payload.data));
  }, [dispatch]);

  return (
    <div style={{ overflowY: 'scroll', maxHeight: '500px' }}>
      <ul>
        {recettes.map(element => (
          <RecetteCard props={element} key={element.id} />
        ))}
      </ul>
    </div>
  );
}
